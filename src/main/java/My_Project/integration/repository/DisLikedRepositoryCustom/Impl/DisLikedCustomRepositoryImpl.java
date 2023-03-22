package My_Project.integration.repository.DisLikedRepositoryCustom.Impl;

import My_Project.integration.entity.*;
import My_Project.integration.repository.DisLikedRepositoryCustom.DisLikedCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static My_Project.integration.entity.QDisLiked.*;
import static My_Project.integration.entity.QLiked.liked;

@Repository
public class DisLikedCustomRepositoryImpl implements DisLikedCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public DisLikedCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Long deleteByPostLidiAndUsers(PostLikeAndDislike postLikeAndDislike, Users users){
        return jpaQueryFactory
                .delete(disLiked)
                .where(disLiked.postLikeAndDislike.eq(postLikeAndDislike).and(disLiked.users.eq(users)))
                .execute();
    }

    public Optional<DisLiked> findDisLikedByUsers(PostLikeAndDislike postLikeAndDislike, Users users) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(disLiked)
                .where(disLiked.users.eq(users).and(disLiked.postLikeAndDislike.eq(postLikeAndDislike)))
                .fetchOne());
    }
}
