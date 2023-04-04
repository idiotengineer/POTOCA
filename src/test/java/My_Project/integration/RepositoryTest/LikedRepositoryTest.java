package My_Project.integration.RepositoryTest;

import My_Project.integration.entity.Liked;
import My_Project.integration.entity.PostLikeAndDislike;
import My_Project.integration.entity.Users;
import My_Project.integration.repository.LikedRepository;
import My_Project.integration.repository.PostLikeAndDislikeRepository;
import My_Project.integration.repository.UsersRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LikedRepositoryTest {

    @Autowired
    private LikedRepository likedRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PostLikeAndDislikeRepository postLikeAndDislikeRepository;

    @Test
    public void findLikedByUsersAndPostLidiTest() throws Exception {
        //given
        Users users = usersRepository.findUsersByEmail("gurtjd97@naver.com").get();
        PostLikeAndDislike postLikeAndDislike = postLikeAndDislikeRepository.findPostLikeAndDislikeByPostInfoPostNumber(236L);
        //when

        Optional<Liked> likedByUsers = likedRepository.findLikedByUsers(postLikeAndDislike, users);
        //then

        Assertions.assertThat(likedByUsers.get()).isNotNull();
    }
}
