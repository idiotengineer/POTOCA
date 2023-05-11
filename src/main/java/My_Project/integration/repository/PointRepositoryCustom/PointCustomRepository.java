package My_Project.integration.repository.PointRepositoryCustom;

import My_Project.integration.entity.PointHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PointCustomRepository {
    public Page<PointHistory> findAllPointHistoryWithPaging(Pageable pageable);
}
