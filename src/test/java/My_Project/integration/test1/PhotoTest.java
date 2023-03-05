package My_Project.integration.test1;

import My_Project.integration.entity.*;
import My_Project.integration.entity.ResponseDto.PostLikeAndDislikeDto;
import My_Project.integration.repository.PhotoRepository;
import My_Project.integration.repository.PostRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

import static My_Project.integration.entity.QPostInfo.postInfo;

@SpringBootTest
@Transactional
public class PhotoTest {

    @Autowired
    EntityManager em;
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    public void init() {
        jpaQueryFactory = new JPAQueryFactory(em);

        Address Address1 = new Address(
                "울산광역시",
                "남구",
                "신정1동",
                "1491-4",
                "xx빌딩"
        );

        Dates dates = new Dates(
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Users users1 = new Users(
                "test9999@naver.com",
                "test1234",
                "테스트",
                "003456789099",
                "0023456789019",
                Address1,
                0L,
                new ArrayList<>(),
                new ArrayList<>(),
                dates
        );


        PostLikeAndDislike postLikeAndDislike = new PostLikeAndDislike(
                null,
                null,
                new HashSet<>(),
                new HashSet<>()
        );

        PostInfo postInfo = new PostInfo(
                null,
                users1,
                "test123",
                "content1",
                dates,
                new HashSet<>(),
                new HashSet<>(),
                postLikeAndDislike
        );

        postLikeAndDislike.setPostInfo(postInfo);

        em.persist(users1);
        em.persist(postInfo);
        em.persist(postLikeAndDislike);

        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    @Test
    public void 게시글의사진삭제테스트() {
        PostInfo test = jpaQueryFactory
                .selectFrom(postInfo)
                .where(postInfo.postTitle.eq("test123"))
                .fetchOne();

        Photo photo1 = new Photo("name1","path1",1L);
        Photo photo2 = new Photo("name2","path2",2L);
        Photo photo3 = new Photo("name3","path3",3L);

        test.addPhoto(photo1);
        test.addPhoto(photo2);
        test.addPhoto(photo3);

        em.persist(photo1);
        em.persist(photo2);
        em.persist(photo3);

        photoRepository.deletePhotoSetByPostInfoId(test.getPostNumber());
    }
}
