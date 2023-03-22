package My_Project.integration.repository;

import My_Project.integration.entity.Liked;
import My_Project.integration.repository.LikedRepositoryCustom.LikedCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikedRepository extends JpaRepository<Liked,Long>, LikedCustomRepository {

}
