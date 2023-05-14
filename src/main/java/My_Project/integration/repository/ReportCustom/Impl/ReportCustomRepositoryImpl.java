package My_Project.integration.repository.ReportCustom.Impl;

import My_Project.integration.entity.QReport;
import My_Project.integration.entity.QUsers;
import My_Project.integration.entity.Report;
import My_Project.integration.repository.ReportCustom.ReportCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static My_Project.integration.entity.QReport.report;
import static My_Project.integration.entity.QUsers.users;

public class ReportCustomRepositoryImpl implements ReportCustomRepository {

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    public Page<Report> findAllWithPaging(Pageable pageable) {
        List<Report> fetch = jpaQueryFactory
                .selectFrom(report)
                .join(report.reportedUser, users).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(report.reportedTime.desc())
                .fetch();

        long l = jpaQueryFactory
                .selectFrom(report)
                .fetchCount();

        return new PageImpl<>(fetch,pageable,l);
    }
}
