package My_Project.integration.repository.PostCommentsRepositoryCustom.Impl;

import My_Project.integration.entity.*;
import My_Project.integration.entity.Dto.BigCommentsDto;
import My_Project.integration.entity.ResponseDto.PostCommentsResponseDto;
import My_Project.integration.repository.PostCommentsRepositoryCustom.PostCommentsRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static My_Project.integration.entity.QBigComments.*;
import static My_Project.integration.entity.QDisLiked.disLiked;
import static My_Project.integration.entity.QLiked.liked;
import static My_Project.integration.entity.QPostComments.postComments;
import static My_Project.integration.entity.QPostInfo.postInfo;
import static My_Project.integration.entity.QPostLikeAndDislike.postLikeAndDislike;
import static My_Project.integration.entity.QUsers.users;

@Repository
public class PostCommentsRepositoryImpl implements PostCommentsRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public PostCommentsRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public PostComments findPostCommentsByIdWithFetch(Long id) {
        return jpaQueryFactory
                .selectFrom(postComments)
                .join(postComments.postLikeAndDislike, postLikeAndDislike).fetchJoin()
                .join(postLikeAndDislike.postInfo, postInfo).fetchJoin()
                .where(postComments.commentNumber.eq(id))
                .fetchOne();
    }

    public PostLikeAndDislike findPostLikeAndDisLikeByIdWithFetch(Long id) {
        return jpaQueryFactory
                .selectFrom(postLikeAndDislike)
                .leftJoin(postLikeAndDislike.disLiked).fetchJoin()
                .leftJoin(postLikeAndDislike.liked).fetchJoin()
                .where(postLikeAndDislike.id.eq(id))
                .fetchOne();
    }


    public PostComments findPostCommentsByIdWithFetchAll(Long id) {
        return jpaQueryFactory
                .selectFrom(postComments)
                .join(postComments.postLikeAndDislike, postLikeAndDislike).fetchJoin()
                .leftJoin(postLikeAndDislike.postInfo).fetchJoin()
                .leftJoin(postLikeAndDislike.disLiked).fetchJoin()
                .leftJoin(postLikeAndDislike.liked).fetchJoin()
                .where(postComments.commentNumber.eq(id))
                .fetchOne();
    }

    public Slice<PostCommentsResponseDto> findCommentsWithPaging(PostInfo postInfo1, Pageable pageable) {
           List<PostCommentsResponseDto> fetch = jpaQueryFactory
                    .select(Projections.constructor(
                            PostCommentsResponseDto.class,
                            postComments
                    ))
                .from(postComments)
                .leftJoin(postComments.postInfo, postInfo).fetchJoin()
                .leftJoin(postComments.postLikeAndDislike, postLikeAndDislike).fetchJoin()
                .leftJoin(postLikeAndDislike.disLiked, disLiked).fetchJoin()
                .leftJoin(postLikeAndDislike.liked, liked).fetchJoin()
                .leftJoin(postLikeAndDislike.postInfo).fetchJoin()
                .leftJoin(postComments.bigCommentsList, bigComments).fetchJoin()
                .leftJoin(bigComments.bigCommentedUser).fetchJoin()
                .leftJoin(bigComments.postComments).fetchJoin()
                .leftJoin(bigComments.postInfo).fetchJoin()
                .leftJoin(bigComments.postLikeAndDislike).fetchJoin()
                .where(postComments.postInfo.eq(postInfo1))
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return checkEndPage(pageable,fetch);
    }

    public Slice<PostCommentsResponseDto> checkEndPage(Pageable pageable, List<PostCommentsResponseDto> results) {
        boolean hasNext = false;

        if (results.size() > pageable.getPageSize()) {
            hasNext = true;
            results.remove(pageable.getPageSize());
        }
        return new SliceImpl<>(results,pageable,hasNext);
    }

    public Optional<PostComments> findPostCommentsV2(Long id) {
        return Optional.ofNullable(jpaQueryFactory
        .selectFrom(postComments)
                .join(postComments.postInfo, postInfo).fetchJoin()
                .join(postComments.postLikeAndDislike, postLikeAndDislike).fetchJoin()
                .leftJoin(postComments.bigCommentsList, bigComments).fetchJoin()
                .leftJoin(bigComments.postLikeAndDislike).fetchJoin()
                .leftJoin(bigComments.postInfo).fetchJoin()
                .leftJoin(bigComments.postComments).fetchJoin()
                .leftJoin(bigComments.bigCommentedUser, users).fetchJoin()
                .where(postComments.postInfo.postNumber.eq(id))
                .fetchOne());
    }

    public Optional<PostComments> findPostCommentsV3(Long id) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(postComments)
                .join(postComments.postInfo, postInfo).fetchJoin()
                .join(postComments.postLikeAndDislike, postLikeAndDislike).fetchJoin()
                .leftJoin(postComments.bigCommentsList, bigComments).fetchJoin()
                .leftJoin(bigComments.postLikeAndDislike).fetchJoin()
                .leftJoin(bigComments.postInfo).fetchJoin()
                .leftJoin(bigComments.postComments).fetchJoin()
                .leftJoin(bigComments.bigCommentedUser, users).fetchJoin()
                .where(postComments.commentNumber.eq(id))
                .fetchOne());
    }
}
