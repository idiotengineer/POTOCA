package My_Project.integration.repository;

import My_Project.integration.entity.PointHistory;
import My_Project.integration.repository.PointRepositoryCustom.PointCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<PointHistory, Long>, PointCustomRepository {

}
