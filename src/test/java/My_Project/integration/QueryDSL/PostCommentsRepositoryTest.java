package My_Project.integration.QueryDSL;

import My_Project.integration.entity.PostComments;
import My_Project.integration.entity.QPostComments;
import My_Project.integration.entity.QPostInfo;
import My_Project.integration.entity.QPostLikeAndDislike;
import My_Project.integration.repository.PostCommentsRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static My_Project.integration.entity.QPostComments.postComments;
import static My_Project.integration.entity.QPostInfo.*;
import static My_Project.integration.entity.QPostLikeAndDislike.*;

@SpringBootTest
@Transactional
public class PostCommentsRepositoryTest {

    @Autowired
    PostCommentsRepository postCommentsRepository;

    @Test
    public void PostCommentsRepository테스트() {
        postCommentsRepository.findAll();
    }

    @Autowired
    EntityManager em;

    @PersistenceUnit
    EntityManagerFactory emf;


    JPAQueryFactory jpaQueryFactory;

    @BeforeEach
    public void init() {
        jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Test
    public void findByID조인테스트() throws Exception {
        //given
        Optional<PostComments> byId = postCommentsRepository.findById(9L);
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        PostComments postCommentsByIdWithFetch = postCommentsRepository.findPostCommentsByIdWithFetch(9L);
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        //when
        postCommentsRepository.findPostLikeAndDisLikeByIdWithFetch(9L);
//        postCommentsByIdWithFetch.getPostLikeAndDislike()
//                .getLikedUser().stream().forEach(
//                    users -> System.out.println("users = " + users)
//                );

        //then

//        postCommentsByIdWithFetch
//                .stream()
//                .forEach(
//                        postComments1 -> System.out.println(postComments1
//                                .getPostLikeAndDislike()
//                                .getLikedUser()
//                                .size()
//                        )
//                );

    }

    @Test
    public void 한방쿼리() throws Exception {
        //given
        Map<String, Object> data = new HashMap<>();
        data.put("id", "11");

        Long postNumber = Long.parseLong(data.get("id").toString());
        PostComments postCommentsByIdWithFetchAll = postCommentsRepository.findPostCommentsByIdWithFetchAll(11L);
        //when


        //then
        System.out.println("1");
    }

    @Test
    public void 한방쿼리2() throws Exception {
        //given
        Map<String, Object> data = new HashMap<>();
        data.put("id", "11");

        Long postNumber = Long.parseLong(data.get("id").toString());

        List<PostComments> fetch = jpaQueryFactory
                .select(postComments)
                .from(postComments)
                .leftJoin(postComments.postLikeAndDislike, postLikeAndDislike)
                .fetchJoin()
                .leftJoin(postLikeAndDislike.postInfo,postInfo)
                .fetchJoin()
                .where(postComments.commentNumber.eq(11L))
                .fetch();

        System.out.println("================================================================================================================================");
        //when

        //then
        boolean loaded = fetch.stream()
                        .allMatch(
                                postComments1 ->
                                        emf.getPersistenceUnitUtil().isLoaded(postComments1.getPostLikeAndDislike())
                        );

        boolean loaded2 = fetch.stream()
                .allMatch(
                        postComments1 -> emf.getPersistenceUnitUtil().isLoaded(postComments1.getPostLikeAndDislike().getPostInfo())
                );
        Assertions.assertThat(loaded).isTrue();
        Assertions.assertThat(loaded2).isTrue();
    }
}
