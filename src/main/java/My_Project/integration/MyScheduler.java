package My_Project.integration;

import My_Project.integration.entity.PostComments;
import My_Project.integration.entity.PostInfo;
import My_Project.integration.entity.Users;
import My_Project.integration.repository.PostRepository;
import My_Project.integration.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@EnableScheduling
public class MyScheduler {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Scheduled(fixedRate = 120000) // 2분 마다 실행
    @Transactional
    public void deleteExpiredObjects() {
        List<PostInfo> expiredPost = postRepository.findExpiredPost();

            if (!expiredPost.isEmpty()) {
            expiredPost.stream()
                    .forEach(
                            (postInfo) -> {
                                if (postInfo.getPoint() != 0L) {
                                    List<Integer> bestPostCommentsList = postInfo.getBestPostCommentsList();
                                    List<PostComments> giveList = new ArrayList<>();

                                    postInfo.getComments()
                                            .stream()
                                            .forEach(
                                                    postComments -> {
                                                        for (int i = 0; i < bestPostCommentsList.size(); i++) {
                                                            if (bestPostCommentsList.get(i) <= postComments.getPostLikeAndDislike().getLiked().size()) {
                                                                giveList.add(postComments);
                                                            }
                                                        }
                                                    }
                                            );

                                    List<PostComments> deduplicatedGiveList = giveList.stream().distinct().collect(Collectors.toList());

                                    if (deduplicatedGiveList.size() > 0) {
                                        Long point = postInfo.getPoint() / giveList.size();

                                        deduplicatedGiveList.stream()
                                                .forEach(
                                                        (postComments) -> {
                                                            Users users = usersRepository.findUsersByEmail(postComments.getPostCommentedUsersEmail()).get();
                                                            Long point1 = users.getPoint() + point;
                                                            users.setPoint(point1);
                                                        }
                                                );
                                    }
                                }
                                    postInfo.setClosed(true);
                            }
                    );

        }
    }
}
