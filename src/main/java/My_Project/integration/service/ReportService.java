package My_Project.integration.service;

import My_Project.integration.entity.*;
import My_Project.integration.entity.DiscriminatedEntity.BigCommentReport;
import My_Project.integration.entity.DiscriminatedEntity.CommentReport;
import My_Project.integration.entity.DiscriminatedEntity.PostReport;
import My_Project.integration.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    BigCommentsRepository bigCommentsRepository;

    @Autowired
    PostCommentsRepository postCommentsRepository;

    public Page<Report> findAllWithPaging(Pageable pageable) {
        return reportRepository.findAllWithPaging(pageable);
    }

    @Transactional
    public Report createNewReport(HashMap<String,Object> map, Optional<Cookie> cookie) {
        Optional<Users> byId = usersRepository.findById(cookie.get().getValue());

        if (byId.isPresent()) {
            Long id = Long.parseLong(map.get("id").toString());
            if (map.get("dtype").equals("POST")) {
                PostInfo postInfo = postRepository.findById(id).get();

                PostReport postReport = new PostReport();
                postReport.setReportedTime(LocalDateTime.now());
                postReport.setReportedUser(byId.get());
                postReport.setPostInfo(postInfo);

                return reportRepository.save(postReport);
            } else if (map.get("dtype").equals("COMMENT")) {
                PostComments postComments = postCommentsRepository.findById(id).get();

                CommentReport commentReport = new CommentReport();
                commentReport.setReportedTime(LocalDateTime.now());
                commentReport.setReportedUser(byId.get());
                commentReport.setPostComments(postComments);

                return reportRepository.save(commentReport);
            } else if (map.get("dtype").equals("BIGCOMMENT")) {
                BigComments bigComments = bigCommentsRepository.findById(id).get();

                BigCommentReport bigCommentReport = new BigCommentReport();
                bigCommentReport.setReportedTime(LocalDateTime.now());
                bigCommentReport.setReportedUser(byId.get());
                bigCommentReport.setBigComments(bigComments);

                return reportRepository.save(bigCommentReport);
            }
        }

        return null;
    }
}
