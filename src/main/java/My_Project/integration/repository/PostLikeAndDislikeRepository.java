package My_Project.integration.repository;

import My_Project.integration.entity.PostInfo;
import My_Project.integration.entity.PostLikeAndDislike;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeAndDislikeRepository extends JpaRepository<PostLikeAndDislike, Long> {

    @EntityGraph(
            attributePaths = {"LikedUser","DisLikedUser"}
    )
    public PostLikeAndDislike findPostLikeAndDislikeByPostInfoPostNumber(Long PostNumber);
}
