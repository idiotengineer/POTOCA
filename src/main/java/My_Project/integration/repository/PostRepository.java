package My_Project.integration.repository;

import My_Project.integration.entity.PostInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostInfo,Long> {
}
