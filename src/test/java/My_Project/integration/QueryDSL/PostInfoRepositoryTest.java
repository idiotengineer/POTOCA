package My_Project.integration.QueryDSL;

import My_Project.integration.entity.PostInfo;
import My_Project.integration.repository.PostRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

@SpringBootTest
@Transactional
public class PostInfoRepositoryTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory jpaQueryFactory;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    public void init() {
        jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Test
    public void postInfo조회Fetch조인테스트V2() throws Exception {
        //given
        Optional<PostInfo> postByIdWithFetchJoinUsedQueryDSLV2 = postRepository.findPostByIdWithFetchJoinUsedQueryDSLV2(237L);
        //when

        //then
    }
}
