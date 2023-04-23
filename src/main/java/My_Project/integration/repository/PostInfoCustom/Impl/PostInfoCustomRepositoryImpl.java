package My_Project.integration.repository.PostInfoCustom.Impl;

import My_Project.integration.entity.*;
import My_Project.integration.entity.Dto.PostDto;
import My_Project.integration.repository.PostInfoCustom.PostInfoCustomRepository;
import ch.qos.logback.core.util.ContextUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import static My_Project.integration.entity.DiscriminatedEntity.QLeagueOfLegendPost.leagueOfLegendPost;
import static My_Project.integration.entity.DiscriminatedEntity.QLostArkPost.lostArkPost;
import static My_Project.integration.entity.DiscriminatedEntity.QMapleStoryPost.mapleStoryPost;
import static My_Project.integration.entity.DiscriminatedEntity.QStarcraftPost.starcraftPost;
import static My_Project.integration.entity.DiscriminatedEntity.QValorantPost.valorantPost;
import static My_Project.integration.entity.QBigComments.bigComments;
import static My_Project.integration.entity.QDisLiked.disLiked;
import static My_Project.integration.entity.QLiked.liked;
import static My_Project.integration.entity.QPhoto.photo;
import static My_Project.integration.entity.QPostComments.postComments;
import static My_Project.integration.entity.QPostInfo.postInfo;
import static My_Project.integration.entity.QPostLikeAndDislike.postLikeAndDislike;
import static My_Project.integration.entity.QUsers.users;

@RequiredArgsConstructor
public class PostInfoCustomRepositoryImpl implements PostInfoCustomRepository {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    @Autowired
    private final EntityManager em;

    public List<PostDto> searchByName(String name) {
        List<PostInfo> resultList = em.createQuery("select p from PostInfo p where p.postedUser.email like concat('%',?1,'%')")
                .setParameter(1, name)
                .getResultList();

        return resultList.stream().map(
                postInfo -> new PostDto(postInfo)
        ).collect(Collectors.toList());
    }

    public List<PostDto> searchByNameV2(String name,String s) {
        List<PostInfo> fetch = jpaQueryFactory.
                selectFrom(postInfo)
                .where(
                        postInfo.postedUser.email.contains(name),
                        eqRegular(s),
                        eqLOL(s),
                        eqLostArk(s),
                        eqValorant(s),
                        eqStarCraft(s),
                        eqMapleStory(s)
                )
                .fetch();

        return fetch.stream().map(
                postInfo1 -> new PostDto(postInfo1)
        ).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchByTitle(String title) {
//        List<PostInfo> list = em.createQuery("select p from PostInfo p where p.postTitle like concat('%',?1,'%')")
//                .setParameter(1, title)
//                .getResultList();

        List<PostInfo> list = jpaQueryFactory.selectFrom(postInfo).fetch();

        return list.stream().map(p -> new PostDto(p))
                .collect(Collectors.toList());
    }

    public List<PostDto> searchByTitleV2(String name,String s) {
        List<PostInfo> fetch = jpaQueryFactory.
                selectFrom(postInfo)
                .where(
                        postInfo.postTitle.contains(name),
                        eqRegular(s),
                        eqLOL(s),
                        eqLostArk(s),
                        eqValorant(s),
                        eqStarCraft(s),
                        eqMapleStory(s)
                )
                .fetch();

        return fetch.stream().map(
                postInfo1 -> new PostDto(postInfo1)
        ).collect(Collectors.toList());
    }

    @Override
    public Optional<PostInfo> findPostByIdWithFetchJoinUsedQueryDSL(Long id){
        PostInfo postInfo1 = jpaQueryFactory
                .selectFrom(postInfo)
                .leftJoin(postInfo.photo, photo).fetchJoin()
                .leftJoin(postInfo.postLikeAndDislike, postLikeAndDislike).fetchJoin()
                .leftJoin(postLikeAndDislike.liked).fetchJoin()
                .leftJoin(postLikeAndDislike.disLiked).fetchJoin()
                .leftJoin(postInfo.comments, postComments).fetchJoin()
                .leftJoin(postInfo.postedUser,users).fetchJoin()
                .where(postInfo.postNumber.eq(id))
                .fetchOne();


        List<PostComments> fetch = jpaQueryFactory
                .selectFrom(postComments)
                .join(postComments.postLikeAndDislike, postLikeAndDislike)
                .fetchJoin()
                .leftJoin(postComments.postInfo, postInfo)
                .fetchJoin()
                .leftJoin(postLikeAndDislike.liked)
                .fetchJoin()
                .leftJoin(postLikeAndDislike.disLiked)
                .fetchJoin()
//                .leftJoin(postLikeAndDislike.postInfo)
//                .fetchJoin()
                .where(postComments.postInfo.postNumber.eq(id))
                .fetch();


        return Optional.of(postInfo1);
    }

    @Override
    public Optional<PostInfo> findPostByIdWithFetchJoinUsedQueryDSLV2(Long id) {
        PostInfo postInfo1 = jpaQueryFactory
                .selectFrom(postInfo)
                .leftJoin(postInfo.photo, photo).fetchJoin()
                .leftJoin(postInfo.postLikeAndDislike, postLikeAndDislike).fetchJoin()
                .leftJoin(postLikeAndDislike.liked).fetchJoin()
                .leftJoin(postLikeAndDislike.disLiked).fetchJoin()
//                .leftJoin(postLikeAndDislike.postInfo).fetchJoin()
                .leftJoin(postInfo.comments, postComments).fetchJoin()
                .leftJoin(postInfo.postedUser, users).fetchJoin()
                .where(postInfo.postNumber.eq(id))
                .fetchOne();

        List<PostComments> fetch = jpaQueryFactory.selectFrom(postComments)
                .join(postComments.postLikeAndDislike, postLikeAndDislike).fetchJoin()
                .join(postComments.postInfo, postInfo).fetchJoin()
                .leftJoin(postLikeAndDislike.liked).fetchJoin()
                .leftJoin(postLikeAndDislike.disLiked).fetchJoin()
//                .leftJoin(postLikeAndDislike.postInfo).fetchJoin()
                .join(postComments.bigCommentsList, bigComments).fetchJoin()
                .where(postComments.postInfo.postNumber.eq(id))
                .fetch();

        List<List<BigComments>> collect = fetch.stream().map(
                        postComments1 -> postComments1.getBigCommentsList())
                .collect(Collectors.toList());


        List<BigComments> fetch1 = jpaQueryFactory.selectFrom(bigComments)
                .join(bigComments.bigCommentedUser, users).fetchJoin()
                .join(bigComments.postLikeAndDislike, postLikeAndDislike).fetchJoin()
                .where(bigComments.in(collect
                        .get(0)
                )).fetch();


        return Optional.of(postInfo1);
    }
    @Override
    public Optional<PostInfo> findPostInfo(Long id) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(postInfo)
                        .join(postInfo.postedUser, users).fetchJoin()
                        .join(postInfo.photo, photo).fetchJoin()
                        .leftJoin(photo.postInfo).fetchJoin()
                        .join(postInfo.postLikeAndDislike, postLikeAndDislike).fetchJoin()
                        .leftJoin(postLikeAndDislike.liked, liked).fetchJoin()
                        .leftJoin(postLikeAndDislike.disLiked, disLiked).fetchJoin()
//                        .leftJoin(postLikeAndDislike.postInfo).fetchJoin()
                        .where(postInfo.postNumber.eq(id))
                        .fetchOne()
        );
    }

    public Optional<PostInfo> findPostV4(Long id) {
        PostInfo postInfo1 = jpaQueryFactory
                .select(postInfo)
                .from(postInfo)
                .join(postInfo.postedUser, users).fetchJoin() // OK
                .leftJoin(postInfo.photo, photo).fetchJoin()
                .leftJoin(postInfo.postLikeAndDislike, postLikeAndDislike).fetchJoin()
                .leftJoin(postLikeAndDislike.liked, liked).fetchJoin()
                .leftJoin(postLikeAndDislike.disLiked, disLiked).fetchJoin()
                .leftJoin(postInfo.comments, postComments).fetchJoin()
                .where(postInfo.postNumber.eq(id))
                .fetchOne();

        List<PostComments> fetch = jpaQueryFactory
                .select(postComments)
                .from(postComments)
                .join(postComments.postInfo, postInfo).fetchJoin()
                .join(postComments.postLikeAndDislike, postLikeAndDislike).fetchJoin()
                .leftJoin(postLikeAndDislike.liked, liked).fetchJoin()
                .leftJoin(postLikeAndDislike.disLiked,disLiked).fetchJoin()
                .leftJoin(postComments.bigCommentsList, bigComments).fetchJoin()
                .where(postComments.in(postInfo1.getComments()))
                .orderBy(postComments.dates.uploadedTime.desc())
                .fetch();

        List<List<BigComments>> collect = fetch.stream()
                .map(
                        postComments1 -> postComments1.getBigCommentsList()
                ).collect(Collectors.toList());

        List<BigComments> collect1 = collect.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());


        List<BigComments> fetch1 = jpaQueryFactory
                .select(bigComments)
                .from(bigComments)
                .join(bigComments.bigCommentedUser, users).fetchJoin()
                .join(bigComments.postComments, postComments).fetchJoin()
                .join(bigComments.postInfo, postInfo).fetchJoin()
                .join(bigComments.postLikeAndDislike, postLikeAndDislike).fetchJoin()
                .leftJoin(postLikeAndDislike.liked, liked).fetchJoin()
                .leftJoin(postLikeAndDislike.disLiked, disLiked).fetchJoin()
                .where(bigComments.in(collect1))
                .fetch();

        return Optional.ofNullable(postInfo1);
    }

    public Page<PostInfo> listingPage(Pageable pageable, String s) {
        List<PostInfo> fetch = jpaQueryFactory
                .selectFrom(postInfo)
                .join(postInfo.postedUser, users).fetchJoin()
                .where(
                        eqLOL(s),
                        eqLostArk(s),
                        eqValorant(s),
                        eqMapleStory(s),
                        eqStarCraft(s),
                        eqRegular(s)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(
                        postInfo.dates.uploadedTime.desc()
                )
                .fetch();
        return new PageImpl<PostInfo>(fetch);
    }

    public BooleanExpression eqStarCraft(String s) {
        if (StringUtils.isNullOrEmpty(s) || !s.equals("STARCRAFT")) {
            return null;
        }
        return postInfo.instanceOf(starcraftPost.getType());
    }

    public BooleanExpression eqLOL(String s) {
        if (StringUtils.isNullOrEmpty(s) || !s.equals("LEAGUEOFLEGEND")) {
            return null;
        }
        return postInfo.instanceOf(leagueOfLegendPost.getType());
    }

    public BooleanExpression eqLostArk(String s) {
        if (StringUtils.isNullOrEmpty(s) || !s.equals("LOSTARK")) {
            return null;
        }

        return postInfo.instanceOf(lostArkPost.getType());
    }

    public BooleanExpression eqValorant(String s) {
        if (StringUtils.isNullOrEmpty(s) || !s.equals("VALORANT")) {
            return null;
        }

        return postInfo.instanceOf(valorantPost.getType());
    }

    public BooleanExpression eqMapleStory(String s) {
        if (StringUtils.isNullOrEmpty(s) || !s.equals("MAPLESTORY")) {
            return null;
        }

        return postInfo.instanceOf(mapleStoryPost.getType());
    }

    public BooleanExpression eqRegular(String s) {
        if (StringUtils.isNullOrEmpty(s) || !s.equals("regular")) {
            return null;
        }

        return postInfo.instanceOf(mapleStoryPost.getType());
    }

//    public List<PostInfo> best4PostForMonth(LocalDateTime now) {
//        LocalDate currentDate = LocalDate.now();
//        LocalDateTime localDateTime = LocalDateTime.now().;
//
//        jpaQueryFactory
//                .select(postInfo)
//                .from(postInfo)
//                .join(postInfo.postLikeAndDislike, postLikeAndDislike).fetchJoin()
//                .join(postInfo.postedUser, users).fetchJoin()
//                .where(
//
//                )
//    }
//
//    public BooleanExpression eqDatesWithToday(LocalDateTime dbValue) {
//        int year = dbValue.getYear();
//        int monthValue = dbValue.getMonthValue();
//        int day = dbValue.getDayOfMonth();
//
//        LocalDateTime now = LocalDateTime.now();
//        if (now.getYear() == year && now.getMonthValue() == monthValue && now.getDayOfMonth() == day) {
//            return
//                    postInfo.dates.uploadedTime.year().eq(dbValue.getYear())
//                            .and(postInfo.dates.uploadedTime.month().eq(dbValue.getMonthValue()))
//                            .and(postInfo.dates.uploadedTime.day().eq(dbValue.getYear()));
//        }
//        return null;
//
//        postInfo.dates.uploadedTime.toLocalDate().isEqual()
//    }


    public List<PostInfo> best4PostForMonth() {
        List<PostInfo> fetch = jpaQueryFactory
                .select(postInfo)
                .from(postInfo)
                .join(postInfo.postLikeAndDislike, postLikeAndDislike).fetchJoin()
                .leftJoin(postInfo.photo, photo).fetchJoin()
                .join(postInfo.postedUser, users).fetchJoin()
//                .where(
//                        eqYearWithNow(),
//                        eqMonthWithNow()
//                )
                .orderBy(postInfo.LikedCount.desc())
                .limit(4)
                .fetch();

        return fetch;
    }

    public BooleanExpression eqYearWithNow() {
//        return postInfo.dates.uploadedTime.eq(A.m);

        LocalDateTime now = LocalDateTime.now();
        Integer year = now.getYear();

        return postInfo.dates.uploadedTime.year().eq(year);
    }

    public BooleanExpression eqMonthWithNow() {
//        return postInfo.dates.uploadedTime.eq(A.m);

        LocalDateTime now = LocalDateTime.now();
        Integer month = now.getMonthValue();

        return postInfo.dates.uploadedTime.month().eq(month);
    }
}
