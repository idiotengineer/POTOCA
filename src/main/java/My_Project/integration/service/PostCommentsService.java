package My_Project.integration.service;

import My_Project.integration.entity.PostComments;
import My_Project.integration.repository.PostCommentsRepository;
import My_Project.integration.repository.PostLikeAndDislikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PostCommentsService {

    @Autowired
    PostCommentsRepository postCommentsRepository;

    @Transactional
    public Optional<PostComments> findPostCommentsById(Long id) {
        PostComments postCommentsByIdWithFetchAll = postCommentsRepository.findPostCommentsByIdWithFetchAll(id);
        return Optional.of(postCommentsByIdWithFetchAll);
    }
}
