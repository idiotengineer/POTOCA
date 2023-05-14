package My_Project.integration.repository;

import My_Project.integration.entity.Report;
import My_Project.integration.repository.ReportCustom.ReportCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report,Long>, ReportCustomRepository {
}
