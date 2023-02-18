package My_Project.integration.QueryDSL;

import My_Project.integration.entity.*;
import My_Project.integration.repository.PostLikeAndDislikeRepository;
import My_Project.integration.repository.PostRepository;
import My_Project.integration.repository.UsersRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static My_Project.integration.entity.QPostInfo.postInfo;
import static My_Project.integration.entity.QUsers.users;

@SpringBootTest
@Transactional
public class Test1 {

    @Autowired
    private EntityManager em;

    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PostLikeAndDislikeRepository postLikeAndDislikeRepository;

    @BeforeEach
    public void init() {
        jpaQueryFactory = new JPAQueryFactory(em);

        Dates dates = new Dates();

        Address Address1 = new Address(
                "서울광역시",
                "강남구",
                "테헤란로 1길",
                "1",
                "xx빌딩"
        );

        Address Address2 = new Address(
                "대전광역시",
                "중구",
                "중앙로 118",
                "6690",
                "xx빌딩"
        );

        Users users1 = new Users(
                "admin1234@naver.com",
                "admin1234",
                "어드민",
                "123456789011",
                "01234567890123",
                Address1,
                0L,
                null,
                null,
                dates
        );

        Users users2 = new Users(
                "user1234@naver.com",
                "user1234",
                "유저1",
                "123456781234",
                "01234567891234",
                Address2,
                0L,
                null,
                null,
                dates
        );

        PostLikeAndDislike postLikeAndDislike1 = new PostLikeAndDislike();
        PostLikeAndDislike postLikeAndDislike2 = new PostLikeAndDislike();

        PostInfo postInfo1 = new PostInfo(
                null,
                users1,
                "title1",
                "content1",
                dates,
                new HashSet<>(),
                new HashSet<>(),
                postLikeAndDislike1
        );

        PostInfo postInfo2 = new PostInfo(
                null,
                users2,
                "title2",
                "content2",
                dates,
                new HashSet<>(),
                new HashSet<>(),
                postLikeAndDislike2
        );
    }

    @Test
    public void 쿼리DSL적용테스트() {
        List<PostInfo> queryDslPostInfo = jpaQueryFactory
                .select(postInfo)
                .from(postInfo)
                .fetch();

        List<Users> queryDslUser = jpaQueryFactory
                .select(users)
                .from(users)
                .fetch();


        List<Users> springDataJPAUsers = usersRepository.findAll();
        List<PostInfo> springDataJPAPostInfo = postRepository.findAll();

        Assertions.assertThat(queryDslUser).containsExactlyElementsOf(springDataJPAUsers);
        Assertions.assertThat(queryDslPostInfo).containsExactlyElementsOf(springDataJPAPostInfo);
    }

    @Test
    public void 페이징개수확인() {
        PageRequest pageable = PageRequest.of(0, 10);

        List<PostInfo> allPostInfoPaging = findAllPostInfoPaging(pageable);
//        List<PostDto> postDtos = allPostInfoPaging
//                .stream().map(
//                        postInfo1 -> new PostDto(postInfo1)
//                ).toList();
    }

    public List<PostInfo> findAllPostInfoPaging(Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(postInfo)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }


    @Test
    public void find_postAPI테스트() {
//        Optional<PostInfo> postInfoByPostNumber = postRepository.findPostInfoByPostNumber(0L);
        PostLikeAndDislike result = postLikeAndDislikeRepository.findPostLikeAndDislikeByPostInfoPostNumber(0L);
    }
}


