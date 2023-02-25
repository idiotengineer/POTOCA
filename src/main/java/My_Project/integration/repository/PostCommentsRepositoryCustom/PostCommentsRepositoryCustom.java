package My_Project.integration.repository.PostCommentsRepositoryCustom;

import My_Project.integration.entity.PostComments;
import My_Project.integration.entity.PostLikeAndDislike;
import org.hibernate.annotations.BatchSize;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostCommentsRepositoryCustom{

    public PostComments findPostCommentsByIdWithFetch(Long id);

    public PostLikeAndDislike findPostLikeAndDisLikeByIdWithFetch(Long id);

    public PostComments findPostCommentsByIdWithFetchAll(Long id);
}
