package My_Project.integration.repository;

import My_Project.integration.entity.PostInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostInfo,Long> {

    Optional<PostInfo> findPostInfoByPostNumber(Long id);
}
