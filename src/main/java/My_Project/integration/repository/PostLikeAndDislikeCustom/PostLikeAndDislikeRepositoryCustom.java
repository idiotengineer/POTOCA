package My_Project.integration.repository.PostLikeAndDislikeCustom;

import My_Project.integration.entity.PostLikeAndDislike;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeAndDislikeRepositoryCustom {

    public PostLikeAndDislike findPostLidiByIdWithFetch(Long id);
}
