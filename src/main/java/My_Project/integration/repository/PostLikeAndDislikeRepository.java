package My_Project.integration.repository;

import My_Project.integration.entity.PostInfo;
import My_Project.integration.entity.PostLikeAndDislike;
import My_Project.integration.repository.PostLikeAndDislikeCustom.PostLikeAndDislikeRepositoryCustom;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeAndDislikeRepository extends JpaRepository<PostLikeAndDislike, Long>, PostLikeAndDislikeRepositoryCustom {

//    @EntityGraph(
//            attributePaths = {"liked","disLiked"}
//    )
//    public PostLikeAndDislike findPostLikeAndDislikeByPostInfoPostNumber(Long PostNumber);
}
