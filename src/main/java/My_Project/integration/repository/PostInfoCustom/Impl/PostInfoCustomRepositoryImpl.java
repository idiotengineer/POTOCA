package My_Project.integration.repository.PostInfoCustom.Impl;

import My_Project.integration.entity.Dto.PostDto;
import My_Project.integration.entity.PostComments;
import My_Project.integration.entity.PostInfo;
import My_Project.integration.repository.PostInfoCustom.PostInfoCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static My_Project.integration.entity.QPhoto.photo;
import static My_Project.integration.entity.QPostComments.postComments;
import static My_Project.integration.entity.QPostInfo.postInfo;
import static My_Project.integration.entity.QPostLikeAndDislike.postLikeAndDislike;
import static My_Project.integration.entity.QUsers.users;

@RequiredArgsConstructor
public class PostInfoCustomRepositoryImpl implements PostInfoCustomRepository {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    @Autowired
    private final EntityManager em;

    public List<PostDto> searchByName(String name) {
        List<PostInfo> resultList = em.createQuery("select p from PostInfo p where p.postedUser.email like concat('%',?1,'%')")
                .setParameter(1, name)
                .getResultList();

        return resultList.stream().map(
                postInfo -> new PostDto(postInfo)
        ).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchByTitle(String title) {
        List<PostInfo> list = em.createQuery("select p from PostInfo p where p.postTitle like concat('%',?1,'%')")
                .setParameter(1, title)
                .getResultList();

                return list.stream().map(p -> new PostDto(p))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PostInfo> findPostByIdWithFetchJoinUsedQueryDSL(Long id){
        PostInfo postInfo1 = jpaQueryFactory
                .selectFrom(postInfo)
                .join(postInfo.photo, photo).fetchJoin()
                .join(postInfo.postLikeAndDislike, postLikeAndDislike).fetchJoin()
                .leftJoin(postLikeAndDislike.LikedUser).fetchJoin()
                .leftJoin(postLikeAndDislike.DisLikedUser).fetchJoin()
                .join(postInfo.comments, postComments).fetchJoin()
                .join(postInfo.postedUser,users).fetchJoin()
                .where(postInfo.postNumber.eq(id))
                .fetchOne();


        List<PostComments> fetch = jpaQueryFactory
                .selectFrom(postComments)
                .join(postComments.postLikeAndDislike, postLikeAndDislike)
                .fetchJoin()
                .leftJoin(postLikeAndDislike.LikedUser)
                .fetchJoin()
                .leftJoin(postLikeAndDislike.DisLikedUser)
                .fetchJoin()
                .leftJoin(postLikeAndDislike.postInfo)
                .fetchJoin()
                .where(postComments.postNumber.eq(postInfo1.getPostNumber()))
                .fetch();

        return Optional.of(postInfo1);
    }
}
