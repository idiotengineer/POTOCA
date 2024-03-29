package My_Project.integration.entity.Dto;

import My_Project.integration.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ModifyDto {
    private String users;
    private String postTitle;
    private String postContent;
    private List<MultipartFile> files;
    private Long postNumber;
}
