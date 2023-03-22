package My_Project.integration.test1;

import My_Project.integration.controller.PostController;
import My_Project.integration.entity.*;
import My_Project.integration.entity.ResponseDto.PostLikeAndDislikeDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.servlet.http.Cookie;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

@SpringBootTest
@Transactional()
public class PostControllerTest {
    @Autowired
    private PostController postController;

    @Autowired
    private EntityManager em;

    @Test
    public void likeAndDisLike테스트() throws Exception {
        //given
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", 10101010);
        map.put("type","like");

        Cookie cookie = new Cookie(
                "users","test9999@naver.com"
        );

        Address Address1 = new Address(
                "울산광역시",
                "남구",
                "신정1동",
                "1491-4",
                "xx빌딩"
        );

        Long id = Long.parseLong(map.get("id").toString());


        Dates dates = new Dates();

        Users users1 = new Users(
                "test9999@naver.com",
                "test1234",
                "테스트",
                "003456789099",
                "00234567890199",
                Address1,
                0L,
                new ArrayList<>(),
                new ArrayList<>(),
                dates,
                new HashSet<>(),
                new HashSet<>()
        );

        PostLikeAndDislike postLikeAndDislike = new PostLikeAndDislike(
                id,
                null,
                new HashSet<>(),
                new HashSet<>()
        );

        PostInfo postInfo1 = new PostInfo(
                id,
                users1,
                "title1",
                "content1",
                dates,
                new HashSet<>(),
                new HashSet<>(),
                postLikeAndDislike
        );
        postLikeAndDislike.setPostInfo(postInfo1);

        PostLikeAndDislikeDto postLikeAndDislikeDto = new PostLikeAndDislikeDto(postLikeAndDislike);

        //when
        String s = postController.likeAndDisLike(map, Optional.of(cookie), null);
        //then
        Assertions.assertThat(s).isEqualTo("1");
    }
}
