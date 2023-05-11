package My_Project.integration.service;

import My_Project.integration.entity.PointHistory;
import My_Project.integration.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PointService {

    @Autowired
    PointRepository pointRepository;

    @Transactional
    public Page<PointHistory> findAllPointHistoryWithPaging(Pageable pageable) {
        return pointRepository.findAllPointHistoryWithPaging(pageable);
    }
}
