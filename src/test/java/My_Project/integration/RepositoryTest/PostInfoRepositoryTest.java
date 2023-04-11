package My_Project.integration.RepositoryTest;

import My_Project.integration.entity.*;
import My_Project.integration.entity.ResponseDto.PostCommentsResponseDto;
import My_Project.integration.repository.PostCommentsRepository;
import My_Project.integration.repository.PostRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

import static My_Project.integration.entity.QBigComments.*;
import static My_Project.integration.entity.QPostComments.postComments;
import static My_Project.integration.entity.QPostInfo.postInfo;
import static My_Project.integration.entity.QPostLikeAndDislike.postLikeAndDislike;
import static My_Project.integration.entity.QUsers.*;

@SpringBootTest
@Transactional
public class PostInfoRepositoryTest {


    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostCommentsRepository postCommentsRepository;


    @Test
    public void postInfo조회Fetch조인테스트V2() throws Exception {
        //given
        PageRequest of = PageRequest.of(0, 5);
        Optional<PostInfo> postInfo = postRepository.findPostInfo(2L);
//        Slice<PostCommentsResponseDto> commentsWithPaging = postCommentsRepository.findCommentsWithPaging(236L, of);
        //when

        Set<Photo> photo = postInfo.get().getPhoto();

        for (Photo photo1 : photo) {
            System.out.println(photo1.getPostInfo().getPostedUser());
        }
        //then
        System.out.println();
    }

    @Test
    public void 게시글조회FETCH테스트() throws Exception {
        //given
        Long id = 295L;
        PostInfo postInfo1 = postRepository.findPostInfoByPostNumber(id).get();

        //when
        PostComments postComments1 = jpaQueryFactory
                .selectFrom(postComments)
                .join(postComments.postInfo, postInfo).fetchJoin()
                .join(postComments.postLikeAndDislike, postLikeAndDislike).fetchJoin()
                .leftJoin(postComments.bigCommentsList, bigComments).fetchJoin()
                .leftJoin(bigComments.postLikeAndDislike).fetchJoin()
                .leftJoin(bigComments.postInfo).fetchJoin()
                .leftJoin(bigComments.postComments).fetchJoin()
                .leftJoin(bigComments.bigCommentedUser, users).fetchJoin()
                .where(postComments.postInfo.postNumber.eq(postInfo1.getPostNumber()))
                .fetchOne();
        //then
        Assertions.assertThat(postComments1).isNotNull();
    }

}
