package My_Project.integration.repository.PostCommentsRepositoryCustom;

import My_Project.integration.entity.PostComments;
import My_Project.integration.entity.PostInfo;
import My_Project.integration.entity.PostLikeAndDislike;
import My_Project.integration.entity.ResponseDto.PostCommentsResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostCommentsRepositoryCustom{

    public PostComments findPostCommentsByIdWithFetch(Long id);

    public PostLikeAndDislike findPostLikeAndDisLikeByIdWithFetch(Long id);

    public PostComments findPostCommentsByIdWithFetchAll(Long id);

    public Slice<PostCommentsResponseDto> findCommentsWithPaging(PostInfo postInfo, Pageable pageable);

    public Optional<PostComments> findPostCommentsV2(Long id);

    public Optional<PostComments> findPostCommentsV3(Long id);
}
