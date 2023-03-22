package My_Project.integration.test1;

import My_Project.integration.entity.*;
import My_Project.integration.repository.PhotoRepository;
import My_Project.integration.repository.PostRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static My_Project.integration.entity.QBigComments.*;
import static My_Project.integration.entity.QPhoto.photo;
import static My_Project.integration.entity.QPostComments.postComments;
import static My_Project.integration.entity.QPostInfo.postInfo;
import static My_Project.integration.entity.QPostLikeAndDislike.postLikeAndDislike;
import static My_Project.integration.entity.QUsers.users;

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
                dates,
                new HashSet<>(),
                new HashSet<>()
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

        Photo photo1 = new Photo("name1", "path1", 1L);
        Photo photo2 = new Photo("name2", "path2", 2L);
        Photo photo3 = new Photo("name3", "path3", 3L);

        test.addPhoto(photo1);
        test.addPhoto(photo2);
        test.addPhoto(photo3);

        em.persist(photo1);
        em.persist(photo2);
        em.persist(photo3);

        photoRepository.deletePhotoSetByPostInfoId(test.getPostNumber());
    }

    @Test
    public void PostlidiFetch조인테스트() {
        Long id = 236L;

        List<PostInfo> fetch = jpaQueryFactory
                .selectFrom(postInfo)
                .join(postInfo.photo, photo)
                .fetchJoin()
                .join(postInfo.postedUser, users)
                .fetchJoin()
                .join(postInfo.postLikeAndDislike, postLikeAndDislike)
                .fetchJoin()
                .fetch();

        List<PostComments> fetch1 = jpaQueryFactory
                .selectFrom(postComments)
                .join(postComments.postLikeAndDislike, postLikeAndDislike)
                .fetchJoin()
                .join(postLikeAndDislike.liked)
                .fetchJoin()
                .join(postLikeAndDislike.disLiked)
                .fetchJoin()
                .where(postComments.postNumber.in(fetch.stream().map(
                        postInfo1 -> postInfo1.getPostNumber()
                ).collect(Collectors.toList())))
                .fetch();

        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    @Test
    public void PostlidiFetch조인테스트2() {
        Long id = 236L;
        List<PostInfo> fetch = jpaQueryFactory.selectFrom(postInfo)
                .join(postInfo.postedUser, users)
                .fetchJoin()
                .join(postInfo.photo, photo)
                .fetchJoin()
                .join(postInfo.postLikeAndDislike, postLikeAndDislike)
                .fetchJoin()
                .join(postInfo.comments, postComments)
                .fetchJoin()
                .where(postInfo.postNumber.eq(id))
                .fetch();


        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        List<PostInfo> fetch2 = jpaQueryFactory.selectFrom(postInfo)
                .join(postInfo.postedUser, users)
                .fetchJoin()
                .join(postInfo.photo, photo)
                .fetchJoin()
                .join(postInfo.postLikeAndDislike, postLikeAndDislike)
                .fetchJoin()
                .join(postInfo.comments, postComments)
                .fetchJoin()
                .where(postInfo.postNumber.eq(id))
                .fetch();
    }

    @Test
    public void PostlidiFetch조인테스트3() {
        Long id = 236L;
        PostInfo postInfo1 = jpaQueryFactory.selectFrom(postInfo)
                .join(postInfo.postedUser, users)
                .fetchJoin()
                .join(postInfo.photo, photo)
                .fetchJoin()
                .join(postInfo.postLikeAndDislike, postLikeAndDislike)
                .fetchJoin()
                .join(postLikeAndDislike.liked)
                .fetchJoin()
                .join(postLikeAndDislike.disLiked)
                .fetchJoin()
                .where(postInfo.postNumber.eq(id))
                .fetchOne();
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        List<PostComments> fetch = jpaQueryFactory
                .selectFrom(postComments)
                .join(postComments.postLikeAndDislike, postLikeAndDislike)
                .fetchJoin()
                .join(postLikeAndDislike.liked)
                .fetchJoin()
                .join(postLikeAndDislike.disLiked)
                .fetchJoin()
                .where(postComments.postNumber.eq(id))
                .fetch();
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    @Test
    public void PostlidiFetch조인테스트4() {
        Long id = 236L;

        PostInfo postInfo1 = jpaQueryFactory
                .selectFrom(postInfo)
                .join(postInfo.photo, photo).fetchJoin()
                .join(postInfo.postLikeAndDislike, postLikeAndDislike).fetchJoin()
                .leftJoin(postLikeAndDislike.liked).fetchJoin()
                .leftJoin(postLikeAndDislike.disLiked).fetchJoin()
                .join(postInfo.comments, postComments).fetchJoin()
                .join(postInfo.postedUser,users).fetchJoin()
                .where(postInfo.postNumber.eq(id))
                .fetchOne();


        List<PostComments> fetch = jpaQueryFactory
                .selectFrom(postComments)
                .join(postComments.postLikeAndDislike, postLikeAndDislike)
                .fetchJoin()
                .leftJoin(postLikeAndDislike.liked)
                .fetchJoin()
                .leftJoin(postLikeAndDislike.disLiked)
                .fetchJoin()
                .leftJoin(postLikeAndDislike.postInfo)
                .fetchJoin()
                .where(postComments.postNumber.eq(postInfo1.getPostNumber()))
                .fetch();

        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        fetch.stream()
                .forEach(
                        postComments1 -> {
                            System.out.println(postComments1.getPostLikeAndDislike().getLiked().size());
                            System.out.println(postComments1.getPostLikeAndDislike().getDisLiked().size());
                        });


        System.out.println(postInfo1.getPostedUser().getEmail());
        System.out.println(postInfo1.getComments().size());
        System.out.println(postInfo1.getPostLikeAndDislike().getLiked().size());
        System.out.println(postInfo1.getPostLikeAndDislike().getDisLiked().size());
        postInfo1.getPhoto()
                .stream().forEach(
                        photo1 -> System.out.println(photo1.getId())
                );

    }
}
