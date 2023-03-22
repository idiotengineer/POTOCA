package My_Project.integration.repository.LikedRepositoryCustom.Impl;

import My_Project.integration.entity.Liked;
import My_Project.integration.entity.PostLikeAndDislike;
import My_Project.integration.entity.QLiked;
import My_Project.integration.entity.Users;
import My_Project.integration.repository.LikedRepositoryCustom.LikedCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static My_Project.integration.entity.QLiked.*;
import static My_Project.integration.entity.QLiked.liked;

@Repository
public class LikedCustomRepositoryImpl implements LikedCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public LikedCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Long deleteByPostLidiAndUsers(PostLikeAndDislike postLikeAndDislike, Users users) {
        return jpaQueryFactory
                .delete(liked)
                .where(liked.postLikeAndDislike.eq(postLikeAndDislike).and(liked.users.eq(users)))
                .execute();
    }

    public Optional<Liked> findLikedByUsers(PostLikeAndDislike postLikeAndDislike,Users users) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(liked)
                .where(liked.users.eq(users).and(liked.postLikeAndDislike.eq(postLikeAndDislike)))
                .fetchOne());
    }

    public long deleteByLiked(Liked liked1) {
       return jpaQueryFactory
                .delete(liked)
                .where(liked.eq(liked1))
                .execute();
    }
}
