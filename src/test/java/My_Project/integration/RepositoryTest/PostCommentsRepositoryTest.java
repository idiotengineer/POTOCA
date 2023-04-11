package My_Project.integration.RepositoryTest;

import My_Project.integration.entity.*;
import My_Project.integration.entity.ResponseDto.PostCommentsResponseDto;
import My_Project.integration.entity.ResponseDto.PostLikeAndDislikeDto;
import My_Project.integration.repository.PostCommentsRepository;
import My_Project.integration.repository.PostRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static My_Project.integration.entity.QBigComments.bigComments;
import static My_Project.integration.entity.QDisLiked.*;
import static My_Project.integration.entity.QLiked.*;
import static My_Project.integration.entity.QPhoto.photo;
import static My_Project.integration.entity.QPostComments.postComments;
import static My_Project.integration.entity.QPostInfo.*;
import static My_Project.integration.entity.QPostLikeAndDislike.*;
import static My_Project.integration.entity.QUsers.users;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PostCommentsRepositoryTest {

    @Autowired
    PostCommentsRepository postCommentsRepository;

    @Test
    public void PostCommentsRepository테스트() {
        postCommentsRepository.findAll();
    }

    @Autowired
    EntityManager em;

    @PersistenceUnit
    EntityManagerFactory emf;

    @Autowired
    PostRepository postRepository;


    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Test
    public void findByID조인테스트() throws Exception {
        //given
        Optional<PostComments> byId = postCommentsRepository.findById(9L);
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        PostComments postCommentsByIdWithFetch = postCommentsRepository.findPostCommentsByIdWithFetch(9L);
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        //when
        postCommentsRepository.findPostLikeAndDisLikeByIdWithFetch(9L);
//        postCommentsByIdWithFetch.getPostLikeAndDislike()
//                .getLikedUser().stream().forEach(
//                    users -> System.out.println("users = " + users)
//                );

        //then

//        postCommentsByIdWithFetch
//                .stream()
//                .forEach(
//                        postComments1 -> System.out.println(postComments1
//                                .getPostLikeAndDislike()
//                                .getLikedUser()
//                                .size()
//                        )
//                );

    }

    @Test
    public void 한방쿼리() throws Exception {
        //given
        Map<String, Object> data = new HashMap<>();
        data.put("id", "11");

        Long postNumber = Long.parseLong(data.get("id").toString());
        PostComments postCommentsByIdWithFetchAll = postCommentsRepository.findPostCommentsByIdWithFetchAll(11L);
        //when


        //then
        System.out.println("1");
    }

    @Test
    public void 한방쿼리2() throws Exception {
        //given
        Map<String, Object> data = new HashMap<>();
        data.put("id", "11");

        Long postNumber = Long.parseLong(data.get("id").toString());

        List<PostComments> fetch = jpaQueryFactory
                .select(postComments)
                .from(postComments)
                .leftJoin(postComments.postLikeAndDislike, postLikeAndDislike)
                .fetchJoin()
                .leftJoin(postLikeAndDislike.postInfo,postInfo)
                .fetchJoin()
                .where(postComments.commentNumber.eq(11L))
                .fetch();

        System.out.println("================================================================================================================================");
        //when

        //then
        boolean loaded = fetch.stream()
                        .allMatch(
                                postComments1 ->
                                        emf.getPersistenceUnitUtil().isLoaded(postComments1.getPostLikeAndDislike())
                        );

        boolean loaded2 = fetch.stream()
                .allMatch(
                        postComments1 -> emf.getPersistenceUnitUtil().isLoaded(postComments1.getPostLikeAndDislike().getPostInfo())
                );
        Assertions.assertThat(loaded).isTrue();
        Assertions.assertThat(loaded2).isTrue();
    }

    @Test
    public void findCommentsWithPagingTest() throws Exception {
        //given
        Long id = 296L;

        //when
        List<PostCommentsResponseDto> fetch = jpaQueryFactory
                .select(Projections.constructor(
                        PostCommentsResponseDto.class,
                        postComments
                ))
                .from(postComments)
                .join(postComments.postInfo, postInfo).fetchJoin()
                .join(postComments.postLikeAndDislike, postLikeAndDislike).fetchJoin()
                .leftJoin(postComments.bigCommentsList, bigComments).fetchJoin()
                .leftJoin(bigComments.postLikeAndDislike).fetchJoin()
                .leftJoin(bigComments.postInfo).fetchJoin()
                .leftJoin(bigComments.postComments).fetchJoin()
                .leftJoin(bigComments.bigCommentedUser, users).fetchJoin()
                .where(postComments.postInfo.postNumber.eq(id))
                .orderBy(postComments.commentNumber.desc())
                .fetch();

        //then
        Assertions.assertThat(fetch).isNotEmpty();
    }

    @Test
    public void postCommentsFindTest() throws Exception {
        //given
        Long id = 2L;
        PageRequest pageable = PageRequest.of(0, 10);

        //when
        PostInfo postInfo1 = jpaQueryFactory
                .selectFrom(postInfo)
                .join(postInfo.postedUser, users).fetchJoin()
                .join(postInfo.photo, photo).fetchJoin()
                .leftJoin(photo.postInfo).fetchJoin()
                .join(postInfo.postLikeAndDislike, postLikeAndDislike).fetchJoin()
                .leftJoin(postLikeAndDislike.liked, liked).fetchJoin()
                .leftJoin(postLikeAndDislike.disLiked, disLiked).fetchJoin()
                .leftJoin(postLikeAndDislike.postInfo).fetchJoin()
                .where(postInfo.postNumber.eq(id))
                .fetchOne();

        List<PostComments> fetch = jpaQueryFactory
                .selectFrom(postComments)
                .join(postComments.postInfo, postInfo).fetchJoin()
                .join(postComments.postLikeAndDislike, postLikeAndDislike).fetchJoin()
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

        Slice<PostCommentsResponseDto> postCommentsResponseDtos = checkEndPage(pageable,
                fetch.stream().map(
                        postComments1 -> new PostCommentsResponseDto(postComments1)
                ).collect(Collectors.toList())
        );
        //then
        System.out.println();
    }

    public Slice<PostCommentsResponseDto> checkEndPage(Pageable pageable, List<PostCommentsResponseDto> results) {
        boolean hasNext = false;

        if (results.size() > pageable.getPageSize()) {
            hasNext = true;
            results.remove(pageable.getPageSize());
        }
        return new SliceImpl<>(results,pageable,hasNext);
    }

    @Test
    public void postCommentsFindTest2() throws Exception {
        //given
        Long id = 1L;
        PageRequest pageable = PageRequest.of(0, 10);

        //when
        PostInfo postInfo1 = jpaQueryFactory
                .selectFrom(postInfo)
                .join(postInfo.postedUser, users).fetchJoin()
                .join(postInfo.photo, photo).fetchJoin()
                .leftJoin(photo.postInfo).fetchJoin()
                .join(postInfo.postLikeAndDislike, postLikeAndDislike).fetchJoin()
                .leftJoin(postLikeAndDislike.liked, liked).fetchJoin()
                .leftJoin(postLikeAndDislike.disLiked, disLiked).fetchJoin()
                .leftJoin(postLikeAndDislike.postInfo).fetchJoin()
                .where(postInfo.postNumber.eq(id))
                .fetchOne();

        List<PostCommentsResponseDto> fetch = jpaQueryFactory
                .select(Projections.constructor(
                        PostCommentsResponseDto.class,
                        postComments
                ))
                .from(postComments)
                .join(postComments.postInfo, postInfo).fetchJoin()
                .join(postComments.postLikeAndDislike, postLikeAndDislike).fetchJoin()
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

        System.out.println();
    }
}
