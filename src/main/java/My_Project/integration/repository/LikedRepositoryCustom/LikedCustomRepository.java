package My_Project.integration.repository.LikedRepositoryCustom;

import My_Project.integration.entity.Liked;
import My_Project.integration.entity.PostLikeAndDislike;
import My_Project.integration.entity.Users;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikedCustomRepository {
    public Long deleteByPostLidiAndUsers(PostLikeAndDislike postLikeAndDislike, Users users);

    public Optional<Liked> findLikedByUsers(PostLikeAndDislike postLikeAndDislike,Users users);

    public long deleteByLiked(Liked liked1);
}
