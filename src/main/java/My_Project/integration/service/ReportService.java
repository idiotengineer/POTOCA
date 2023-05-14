package My_Project.integration.service;

import My_Project.integration.entity.Report;
import My_Project.integration.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    ReportRepository reportRepository;

    public Page<Report> findAllWithPaging(Pageable pageable) {
        return reportRepository.findAllWithPaging(pageable);
    }
}
