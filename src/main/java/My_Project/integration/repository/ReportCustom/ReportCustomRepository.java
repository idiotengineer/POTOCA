package My_Project.integration.repository.ReportCustom;

import My_Project.integration.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportCustomRepository {

    public Page<Report> findAllWithPaging(Pageable pageable);
}
