package My_Project.integration.repository.PostInfoCustom;

import My_Project.integration.entity.Dto.PostDto;
import My_Project.integration.entity.PostInfo;

import java.util.List;
import java.util.Optional;

public interface PostInfoCustomRepository {
    List<PostDto> searchByName(String name);

    List<PostDto> searchByTitle(String title);

    Optional<PostInfo> findPostByIdWithFetchJoinUsedQueryDSL(Long id);
}
