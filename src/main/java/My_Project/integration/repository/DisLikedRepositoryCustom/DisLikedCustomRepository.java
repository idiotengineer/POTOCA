package My_Project.integration.repository.DisLikedRepositoryCustom;

import My_Project.integration.entity.DisLiked;
import My_Project.integration.entity.Liked;
import My_Project.integration.entity.PostLikeAndDislike;
import My_Project.integration.entity.Users;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DisLikedCustomRepository {
    public Long deleteByPostLidiAndUsers(PostLikeAndDislike postLikeAndDislike, Users users);

    public Optional<DisLiked> findDisLikedByUsers(PostLikeAndDislike postLikeAndDislike, Users users);
}
