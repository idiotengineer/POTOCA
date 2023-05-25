package My_Project.integration.repository.BigCommentsRepositorCustom.Impl;

import My_Project.integration.entity.*;
import My_Project.integration.repository.BigCommentsRepositorCustom.BigCommentsCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static My_Project.integration.entity.QBigComments.*;
import static My_Project.integration.entity.QDisLiked.disLiked;
import static My_Project.integration.entity.QLiked.*;
import static My_Project.integration.entity.QPhoto.*;
import static My_Project.integration.entity.QPostComments.postComments;
import static My_Project.integration.entity.QPostInfo.*;
import static My_Project.integration.entity.QPostLikeAndDislike.postLikeAndDislike;
import static My_Project.integration.entity.QUsers.*;

@Repository
public class BigCommentsCustomRepositoryImpl implements BigCommentsCustomRepository {

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    private EntityManager em;

    public Optional<BigComments> findBigCommentsById(Long id) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(bigComments)
                .join(bigComments.postLikeAndDislike , postLikeAndDislike).fetchJoin()
//                .leftJoin(postLikeAndDislike.postInfo).fetchJoin()
                .leftJoin(postLikeAndDislike.disLiked).fetchJoin()
                .leftJoin(postLikeAndDislike.liked).fetchJoin()
                .where(bigComments.bigCommentsNumber.eq(id))
                .fetchOne());
    }

    public List<BigComments> findBigCommentsByUsersEmailList(List<String> usersEmailList) {
        return jpaQueryFactory
                .selectFrom(bigComments)
                .join(bigComments.postLikeAndDislike , postLikeAndDislike).fetchJoin()
//                .leftJoin(postLikeAndDislike.postInfo).fetchJoin()
                .leftJoin(postLikeAndDislike.disLiked).fetchJoin()
                .leftJoin(postLikeAndDislike.liked).fetchJoin()
                .where(bigComments.bigCommentedUser.email.in(usersEmailList))
                .fetch();
    }

    public long deleteBigCommentsListByUsersEmailList(List<String> userEmailList) {
        jpaQueryFactory
                .delete(bigComments)
                .where(bigComments.bigCommentedUser.email.in(userEmailList))
                .execute();

        jpaQueryFactory
                .delete(liked)
                .where(liked.users.email.in(userEmailList))
                .execute();

        jpaQueryFactory
                .delete(disLiked)
                .where(disLiked.users.email.in(userEmailList))
                .execute();

        jpaQueryFactory
                .delete(postComments)
                .where(postComments.postCommentedUsersEmail.in(userEmailList))
                .execute();

        List<PostInfo> fetch = jpaQueryFactory
                .selectFrom(postInfo)
                .leftJoin(postInfo.photo, photo).fetchJoin()
                .where(postInfo.postedUser.email.in(userEmailList))
                .fetch();

        fetch
                .stream()
                        .forEach(
                                postInfo1 -> postInfo1.setBestPostCommentsList(null)
                        );

        jpaQueryFactory
                .delete(photo)
                .where(photo.postInfo.in(fetch))
                .execute();

        jpaQueryFactory
                .delete(postInfo)
                .where(postInfo.postedUser.email.in(userEmailList))
                .execute();

        long execute = jpaQueryFactory
                .delete(users)
                .where(users.email.in(userEmailList))
                .execute();

        return execute;
    }
}
