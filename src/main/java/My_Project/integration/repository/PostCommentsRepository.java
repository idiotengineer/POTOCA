package My_Project.integration.repository;

import My_Project.integration.entity.PostComments;
import My_Project.integration.entity.PostInfo;
import My_Project.integration.repository.PostCommentsRepositoryCustom.PostCommentsRepositoryCustom;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentsRepository extends JpaRepository<PostComments, Long>, PostCommentsRepositoryCustom {

    @EntityGraph(attributePaths =
            {"postLikeAndDislike","postInfo"})
    public PostComments findPostCommentsByCommentNumber(Long id);


    public List<PostComments> findPostCommentsByPostInfo(PostInfo postInfo);
}
