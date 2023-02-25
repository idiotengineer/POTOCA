package My_Project.integration.repository.PostLikeAndDislikeCustom.Impl;

import My_Project.integration.entity.PostLikeAndDislike;
import My_Project.integration.entity.QPostLikeAndDislike;
import My_Project.integration.repository.PostLikeAndDislikeCustom.PostLikeAndDislikeRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static My_Project.integration.entity.QPostLikeAndDislike.postLikeAndDislike;

@Repository
public class PostLikeAndDislikeRepositoryImpl implements PostLikeAndDislikeRepositoryCustom {

    private JPAQueryFactory jpaQueryFactory;

    public PostLikeAndDislikeRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public PostLikeAndDislike findPostLidiById(Long id) {
        return jpaQueryFactory
                .selectFrom(postLikeAndDislike)
                .where(postLikeAndDislike.id.eq(id))
                .fetchOne();
    }

    public PostLikeAndDislike findPostLidiByIdWithFetch(Long id) {
        return jpaQueryFactory
                .selectFrom(postLikeAndDislike)
                .join(postLikeAndDislike.LikedUser).fetchJoin()
                .join(postLikeAndDislike.DisLikedUser).fetchJoin()
                .fetchOne();
    }
}
