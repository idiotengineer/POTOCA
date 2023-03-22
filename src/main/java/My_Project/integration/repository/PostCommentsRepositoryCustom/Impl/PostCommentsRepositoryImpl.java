package My_Project.integration.repository.PostCommentsRepositoryCustom.Impl;

import My_Project.integration.entity.PostComments;
import My_Project.integration.entity.PostLikeAndDislike;
import My_Project.integration.repository.PostCommentsRepositoryCustom.PostCommentsRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import static My_Project.integration.entity.QPostComments.postComments;
import static My_Project.integration.entity.QPostInfo.postInfo;
import static My_Project.integration.entity.QPostLikeAndDislike.postLikeAndDislike;

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
}
