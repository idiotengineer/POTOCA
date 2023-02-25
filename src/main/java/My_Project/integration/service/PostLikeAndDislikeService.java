package My_Project.integration.service;

import My_Project.integration.repository.PostLikeAndDislikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostLikeAndDislikeService {

    @Autowired
    PostLikeAndDislikeRepository postLikeAndDislikeRepository;


    public void getPostLiDiByIdWithFetch(Long id) {
            postLikeAndDislikeRepository.findPostLidiByIdWithFetch(id);
    }
}
