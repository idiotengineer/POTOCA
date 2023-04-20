package My_Project.integration.repository;

import My_Project.integration.entity.PostInfo;
import My_Project.integration.repository.PostInfoCustom.PostInfoCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<PostInfo,Long>, PostInfoCustomRepository {

    @EntityGraph(
            attributePaths = {"photo", "postLikeAndDislike","comments","postedUser"}
    )
    Optional<PostInfo> findPostInfoByPostNumber(Long id);

    List<PostInfo> findByPostTitleContaining(String title);
    // 스프링 데이터 JPA에서 Containing을 쓰게 되면 쿼리로 Like문을 쓸 수 있다!

    Page<PostInfo> findAllByOrderByPostNumber(Pageable pageable);

    Optional<PostInfo> findPostInfoWithSpringDataJpaByPostNumber(Long id);

    Page<PostInfo> findAllByOrderByDatesUploadedTimeDesc(Pageable pageable);

    @EntityGraph(
            attributePaths = {"postLikeAndDislike","postLikeAndDislike.liked","postLikeAndDislike.liked.postLikeAndDislike"}
    )
    Page<PostInfo> findAllByDtypeOrderByDatesUploadedTimeDesc(String dtype,Pageable pageable);

//    Page<PostInfo> findPostInfo(String postType,Pageable pageable);


    @EntityGraph(
            attributePaths = {"postLikeAndDislike", "postLikeAndDislike.liked"}
    )
    public Optional<PostInfo> findPostByPostNumber(Long postNumber);
}
