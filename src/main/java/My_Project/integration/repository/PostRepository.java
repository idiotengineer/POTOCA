package My_Project.integration.repository;

import My_Project.integration.entity.PostInfo;
import My_Project.integration.repository.PostInfoCustom.PostInfoCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostInfo,Long>, PostInfoCustomRepository {

    Optional<PostInfo> findPostInfoByPostNumber(Long id);

    List<PostInfo> findByPostTitleContaining(String title);
    // 스프링 데이터 JPA에서 Containing을 쓰게 되면 쿼리로 Like문을 쓸 수 있다!
}
