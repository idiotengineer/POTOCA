package My_Project.integration.repository.UserCustom.Impl;

import My_Project.integration.entity.*;
import My_Project.integration.repository.UserCustom.UserCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static My_Project.integration.entity.QBigComments.bigComments;
import static My_Project.integration.entity.QDisLiked.*;
import static My_Project.integration.entity.QLiked.*;
import static My_Project.integration.entity.QPointHistory.*;
import static My_Project.integration.entity.QPostComments.*;
import static My_Project.integration.entity.QPostInfo.postInfo;
import static My_Project.integration.entity.QPostLikeAndDislike.postLikeAndDislike;
import static My_Project.integration.entity.QUsers.users;

@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

    @Autowired
    private final EntityManager em;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean duplicateCheck(Users users) {
        Query query1 = em.createQuery("select u from Users u where u.email = :name", Users.class)
                .setParameter("name", users.getEmail());
        List resultList1 = query1.getResultList();
        if (!resultList1.isEmpty()) return false;

        Query query2 = em.createQuery("select u from Users u where u.phoneNumber= :name", Users.class)
                .setParameter("name", users.getPhoneNumber());
        List resultList2 = query2.getResultList();
        if (!resultList2.isEmpty()) return false;

        Query query3 = em.createQuery("select u from Users u where u.ssn = :name", Users.class)
                .setParameter("name", users.getSsn());
        List resultList3 = query3.getResultList();
        if (!resultList3.isEmpty()) return false;

        return true;
    }

//    @Override
//    public boolean checkUserInfo(String id,String password) {
//        try {
//            System.out.println("Service의 LoginDto.getId() : " + id);
//            System.out.println("Service의 LoginDto.getPassword() : " + password);
//
//            Optional <Users> matchedUser = Optional.ofNullable(em.createQuery("select u from Users u where u.id = :id", Users.class)
//                    .setParameter("id", id)
//                    .getSingleResult());
//
//            if(matchedUser.get().getPassword().equals(password)){
//                return true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return false;
//    }

    public Page<Users> findAllUsersWithPaging(Pageable pageable) {
        List<Users> fetch = jpaQueryFactory
                .select(users)
                .from(users)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(users.dates.uploadedTime.desc())
                .fetch();

        long l = jpaQueryFactory
                .select(users)
                .from(users)
                .fetchCount();

        return new PageImpl<>(fetch, pageable, l);
    }

    public long deleteUserList(List<String> userEmailList) {
//        long execute = jpaQueryFactory
//                .delete(postInfo)
//                .where(postInfo.postedUser.email.in(userEmailList))
//                .execute();

        return jpaQueryFactory
                .delete(users)
                .where(users.email.in(userEmailList))
                .execute();
    }

    public List<Users> findUserByEmailList(List<String> userEmailList) {
        return jpaQueryFactory
                .select(users)
                .from(users)
                .leftJoin(users.disLiked, disLiked).fetchJoin()
                .leftJoin(users.pointHistories, pointHistory).fetchJoin()
                .leftJoin(users.uploadedPost, postInfo).fetchJoin()
                .leftJoin(users.liked, liked).fetchJoin()
                .where(users.email.in(userEmailList))
                .distinct()
                .fetch();
    }

    public Users findUsersByEmailCustom(String s) {
        return jpaQueryFactory
                .selectFrom(users)
                .leftJoin(users.uploadedPost, postInfo).fetchJoin()
                .leftJoin(users.liked, liked).fetchJoin()
                .leftJoin(users.disLiked, disLiked).fetchJoin()
                .leftJoin(users.pointHistories, pointHistory).fetchJoin()
                .where(users.email.eq(s))
                .fetchOne();
    }
}
