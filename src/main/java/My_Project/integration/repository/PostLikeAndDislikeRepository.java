package My_Project.integration.repository;

import My_Project.integration.entity.PostLikeAndDislike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeAndDislikeRepository extends JpaRepository<PostLikeAndDislike, Long> {
}
