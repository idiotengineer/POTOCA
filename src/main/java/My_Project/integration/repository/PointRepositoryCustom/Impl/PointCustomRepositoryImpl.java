package My_Project.integration.repository.PointRepositoryCustom.Impl;

import My_Project.integration.entity.PointHistory;
import My_Project.integration.entity.QPointHistory;
import My_Project.integration.repository.PointRepositoryCustom.PointCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static My_Project.integration.entity.QPointHistory.pointHistory;

public class PointCustomRepositoryImpl implements PointCustomRepository {

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    public Page<PointHistory> findAllPointHistoryWithPaging(Pageable pageable) {
        List<PointHistory> fetch = jpaQueryFactory
                .select(pointHistory)
                .from(pointHistory)
                .orderBy(pointHistory.setUppedTime.desc())
                .fetch();

        long l = jpaQueryFactory
                .selectFrom(pointHistory)
                .fetchCount();

        return new PageImpl<>(fetch,pageable,l);
    }
}
