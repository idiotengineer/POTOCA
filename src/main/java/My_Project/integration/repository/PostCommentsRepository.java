package My_Project.integration.repository;

import My_Project.integration.entity.PostComments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentsRepository extends JpaRepository<PostComments,Long> {
}
