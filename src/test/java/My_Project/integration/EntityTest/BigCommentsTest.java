package My_Project.integration.EntityTest;

import My_Project.integration.entity.*;
import My_Project.integration.entity.Dto.addBigCommentsDto;
import My_Project.integration.repository.BigCommentsRepository;
import My_Project.integration.repository.PostCommentsRepository;
import My_Project.integration.repository.UsersRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class BigCommentsTest {

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    BigCommentsRepository bigCommentsRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PostCommentsRepository postCommentsRepository;

    @Test
    @Transactional
    public void BigCommentsSaveTest() throws Exception {
        //given
        Long postNumber = 296L;
        Long commentNumber = 29L;

        String s = "gurtjd97@naver.com";
        addBigCommentsDto dto =
                addBigCommentsDto
                        .builder()
                .comment("대댓글")
                .comment_number(commentNumber)
                .post_number(postNumber)
                .build();

        Dates dates = new Dates(LocalDateTime.now(), LocalDateTime.now());
        Users users = usersRepository.findUsersByEmail(s).get();
        PostComments postCommentsByIdWithFetch = postCommentsRepository.findPostCommentsV2(dto.getPost_number()).get();

        PostLikeAndDislike postLikeAndDislike = new PostLikeAndDislike();
        postLikeAndDislike.setPostInfo(postCommentsByIdWithFetch.getPostInfo());


        BigComments bigComments = BigComments.builder()
                .bigCommentedUser(users)
                .content(dto.getComment())
                .postLikeAndDislike(postLikeAndDislike)
                .dates(dates)
                .postInfo(postCommentsByIdWithFetch.getPostInfo())
                .postComments(postCommentsByIdWithFetch)
                .build();

        //when
        BigComments save = bigCommentsRepository.save(bigComments);

        //then
        System.out.println("");
    }
}
