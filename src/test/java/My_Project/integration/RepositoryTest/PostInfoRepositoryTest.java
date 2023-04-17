package My_Project.integration.RepositoryTest;

import My_Project.integration.entity.*;
import My_Project.integration.entity.DiscriminatedEntity.*;
import My_Project.integration.entity.ResponseDto.PostCommentsResponseDto;
import My_Project.integration.repository.PostCommentsRepository;
import My_Project.integration.repository.PostRepository;
import ch.qos.logback.core.util.ContextUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static My_Project.integration.entity.DiscriminatedEntity.QDiabloPost.*;
import static My_Project.integration.entity.DiscriminatedEntity.QLeagueOfLegendPost.*;
import static My_Project.integration.entity.DiscriminatedEntity.QLostArkPost.*;
import static My_Project.integration.entity.DiscriminatedEntity.QMapleStoryPost.*;
import static My_Project.integration.entity.DiscriminatedEntity.QStarcraftPost.*;
import static My_Project.integration.entity.DiscriminatedEntity.QValorantPost.*;
import static My_Project.integration.entity.QBigComments.*;
import static My_Project.integration.entity.QDisLiked.disLiked;
import static My_Project.integration.entity.QLiked.liked;
import static My_Project.integration.entity.QPhoto.photo;
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


@Test
public void findPostV4Test() throws Exception {
    //given
    Long id = 1L;

    //when
        PostInfo postInfo1 = jpaQueryFactory
                .selectFrom(postInfo)
                .leftJoin(postInfo.postedUser, users).fetchJoin()
                .leftJoin(postInfo.postLikeAndDislike, postLikeAndDislike).fetchJoin()
                .leftJoin(postLikeAndDislike.liked).fetchJoin()
                .leftJoin(postLikeAndDislike.disLiked).fetchJoin()
                .leftJoin(postInfo.comments, postComments).fetchJoin()
                .leftJoin(postComments.postInfo).fetchJoin()
                .leftJoin(postComments.postLikeAndDislike).fetchJoin()
//                .leftJoin(postComments.postLikeAndDislike.disLiked).fetchJoin()
//                .leftJoin(postComments.postLikeAndDislike.liked).fetchJoin()
//                .leftJoin(postComments.postLikeAndDislike.postInfo).fetchJoin()
                .leftJoin(postComments.bigCommentsList, bigComments).fetchJoin()
                .leftJoin(bigComments.postComments).fetchJoin()
                .leftJoin(bigComments.bigCommentedUser).fetchJoin()
                .leftJoin(bigComments.postInfo).fetchJoin()
                .leftJoin(bigComments.postLikeAndDislike, postLikeAndDislike).fetchJoin()
                .leftJoin(postLikeAndDislike.liked, liked).fetchJoin()
                .leftJoin(postLikeAndDislike.disLiked, disLiked).fetchJoin()
                .leftJoin(postLikeAndDislike.postInfo).fetchJoin()
                .leftJoin(postInfo.photo, photo).fetchJoin()
                .leftJoin(photo.postInfo).fetchJoin()
                .where(postInfo.postNumber.eq(id))
                .fetchOne();
    //then
    Assertions.assertThat(postInfo1).isNotNull();
    }

    @Test
    public void q() throws Exception {
        //given
        PageRequest of = PageRequest.of(0, 10);

        String parameter = "STARCRAFT";

        List<PostInfo> fetch = jpaQueryFactory.selectFrom(postInfo)
                .where(
                        eqLOL(parameter),
                        eqLostArk(parameter),
                        eqValorant(parameter),
                        eqMapleStory(parameter),
                        eqStarCraft(parameter)
                )
                .leftJoin(postInfo.postedUser, users).fetchJoin()
                .leftJoin(postInfo.postLikeAndDislike, postLikeAndDislike).fetchJoin()
                .leftJoin(postInfo.photo, photo).fetchJoin()

                .offset(of.getOffset())
                .limit(of.getPageSize())
                .fetch();
        //when

        //then

        Assertions.assertThat(fetch).isNotEmpty();

    }

    public BooleanExpression eqStarCraft(String s) {
        if (StringUtils.isBlank(s) || !s.equals("STARCRAFT")) {
            return null;
        }
        return postInfo.instanceOf(starcraftPost.getType());
    }

    public BooleanExpression eqLOL(String s) {
        if (StringUtils.isBlank(s) || !s.equals("LEAGUEOFLEGEND")) {
            return null;
        }
        return postInfo.instanceOf(leagueOfLegendPost.getType());
    }

    public BooleanExpression eqLostArk(String s) {
        if (StringUtils.isBlank(s) || !s.equals("LOSTARK")) {
            return null;
        }

        return postInfo.instanceOf(lostArkPost.getType());
    }

    public BooleanExpression eqValorant(String s) {
        if (StringUtils.isBlank(s) || !s.equals("VALORANT")) {
            return null;
        }

        return postInfo.instanceOf(valorantPost.getType());
    }

    public BooleanExpression eqMapleStory(String s) {
        if (StringUtils.isBlank(s) || !s.equals("MAPLESTORY")) {
            return null;
        }

        return postInfo.instanceOf(mapleStoryPost.getType());
    }
}
