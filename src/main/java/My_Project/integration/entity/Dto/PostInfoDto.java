package My_Project.integration.entity.Dto;

import My_Project.integration.entity.PostComments;
import My_Project.integration.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostInfoDto {
    private Users users;
    private String postTitle;
    private String postContent;
    private List<MultipartFile> files;

    public PostInfoDto(ModifyDto modifyDto,Users users) {
        this.setUsers(users);
        this.setPostTitle(modifyDto.getPostTitle());
        this.setPostContent(modifyDto.getPostContent());

        if (!modifyDto.getFiles().isEmpty()) {
            files = new ArrayList<>(modifyDto.getFiles());
        } else {
            files = new ArrayList<>();
        }

    }

}
