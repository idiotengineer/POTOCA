package My_Project.integration.repository.BigCommentsRepositorCustom.Impl;

import My_Project.integration.entity.BigComments;
import My_Project.integration.entity.QBigComments;
import My_Project.integration.repository.BigCommentsRepositorCustom.BigCommentsCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static My_Project.integration.entity.QBigComments.*;
import static My_Project.integration.entity.QPostComments.postComments;
import static My_Project.integration.entity.QPostLikeAndDislike.postLikeAndDislike;

@Repository
public class BigCommentsCustomRepositoryImpl implements BigCommentsCustomRepository {

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    public Optional<BigComments> findBigCommentsById(Long id) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(bigComments)
                .join(bigComments.postLikeAndDislike , postLikeAndDislike).fetchJoin()
                .leftJoin(postLikeAndDislike.postInfo).fetchJoin()
                .leftJoin(postLikeAndDislike.disLiked).fetchJoin()
                .leftJoin(postLikeAndDislike.liked).fetchJoin()
                .where(bigComments.bigCommentsNumber.eq(id))
                .fetchOne());
    }
}
