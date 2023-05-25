package My_Project.integration.RepositoryTest;

import My_Project.integration.entity.*;
import My_Project.integration.repository.UsersRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static My_Project.integration.entity.QBigComments.bigComments;
import static My_Project.integration.entity.QDisLiked.disLiked;
import static My_Project.integration.entity.QLiked.liked;
import static My_Project.integration.entity.QPhoto.photo;
import static My_Project.integration.entity.QPointHistory.pointHistory;
import static My_Project.integration.entity.QPostComments.postComments;
import static My_Project.integration.entity.QPostInfo.postInfo;
import static My_Project.integration.entity.QUsers.users;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class UsersRepositoryTest {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Test
    public void deleteUserListTest() throws Exception {
        //given
        List<String> userEmailList = new ArrayList<>();
        userEmailList.add("donkey1072@naver.com");

        //when
        long l = usersRepository.deleteUserList(userEmailList);
        //then
    }

    @Test
    public void findUserByEmailListTest() throws Exception {
        //given
        List<String> userEmailList = new ArrayList<>();
        userEmailList.add("donkey1072@naver.com");

        //when
        List<Users> fetch = jpaQueryFactory
                .select(users)
                .from(users)
                .leftJoin(users.disLiked, disLiked).fetchJoin()
                .leftJoin(users.pointHistories, pointHistory).fetchJoin()
                .leftJoin(users.uploadedPost, postInfo).fetchJoin()
                .leftJoin(users.liked, liked).fetchJoin()
                .where(users.email.in(userEmailList))
                .distinct()
                .fetch();

        //then
        System.out.println(fetch);
    }

    @Test
    public void deleteUserTest() throws Exception {
        List<String> userEmailList = new ArrayList<>();
        userEmailList.add("donkey1072@naver.com");
        
        //given
        List<BigComments> fetch1 = jpaQueryFactory
                .selectFrom(bigComments)
                .where(bigComments.bigCommentedUser.email.in(userEmailList))
                .distinct()
                .fetch();

        List<Liked> fetch2 = jpaQueryFactory
                .selectFrom(liked)
                .where(liked.users.email.in(userEmailList))
                .fetch();

        List<DisLiked> fetch3 = jpaQueryFactory
                .selectFrom(disLiked)
                .where(disLiked.users.email.in(userEmailList))
                .fetch();

        List<PostComments> fetch4 = jpaQueryFactory
                .selectFrom(postComments)
                .where(postComments.postCommentedUsersEmail.in(userEmailList))
                .fetch();

        List<PostInfo> fetch5 = jpaQueryFactory
                .selectFrom(postInfo)
                .leftJoin(postInfo.photo, photo).fetchJoin()
                .where(postInfo.postedUser.email.in(userEmailList))
                .fetch();

        fetch5
                .stream()
                .forEach(
                        postInfo1 -> postInfo1.setBestPostCommentsList(null)
                );

        List<Photo> fetch6 = jpaQueryFactory
                .selectFrom(photo)
                .where(photo.postInfo.in(fetch5))
                .fetch();

        List<PostInfo> fetch7 = jpaQueryFactory
                .selectFrom(postInfo)
                .where(postInfo.postedUser.email.in(userEmailList))
                .fetch();

        List<Users> fetch8 = jpaQueryFactory
                .selectFrom(users)
                .where(users.email.in(userEmailList))
                .fetch();
        //when

        System.out.println();

        //then
    }
}
