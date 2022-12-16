package My_Project.integration.entity.Dto;

import My_Project.integration.entity.PostComments;
import My_Project.integration.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostInfoDto {
    private String postTitle;
    private String postContent;
    private List<Byte> image;
}
