package My_Project.integration.repository;

import My_Project.integration.entity.DisLiked;
import My_Project.integration.repository.DisLikedRepositoryCustom.DisLikedCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisLikedRepository extends JpaRepository<DisLiked,Long>, DisLikedCustomRepository {

}
