package My_Project.integration.repository;

import My_Project.integration.entity.BigComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BigCommentsRepository extends JpaRepository<BigComments, Long> {

}
