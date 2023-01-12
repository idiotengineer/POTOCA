package My_Project.integration.repository.PostInfoCustom;

import My_Project.integration.entity.Dto.PostDto;

import java.util.List;

public interface PostInfoCustomRepository {
    List<PostDto> searchByName(String name);

    List<PostDto> searchByTitle(String title);
}
