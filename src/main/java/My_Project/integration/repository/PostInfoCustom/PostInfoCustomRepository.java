package My_Project.integration.repository.PostInfoCustom;

import My_Project.integration.entity.Dto.PostDto;
import My_Project.integration.entity.PostInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostInfoCustomRepository {
    List<PostDto> searchByName(String name);

    List<PostDto> searchByTitle(String title);

    Optional<PostInfo> findPostByIdWithFetchJoinUsedQueryDSL(Long id);

    Optional<PostInfo> findPostByIdWithFetchJoinUsedQueryDSLV2(Long id);

    public Optional<PostInfo> findPostInfo(Long id);

    public Optional<PostInfo> findPostV4(Long id);

    public Page<PostInfo> listingPage(Pageable pageable, String s);
}
