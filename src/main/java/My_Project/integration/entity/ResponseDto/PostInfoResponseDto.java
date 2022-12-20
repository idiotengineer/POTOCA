package My_Project.integration.entity.ResponseDto;

import My_Project.integration.entity.PostInfo;

import java.util.List;

public class PostInfoResponseDto {

    private Long id;
    private String member;
    private String title;
    private String content;
    private List<Long> fileId;

    public PostInfoResponseDto(PostInfo entity, List<Long> fileId) {
        this.id = entity.getPostNumber();
        this.member = entity.getPostedUser().getName();
        this.title = entity.getPostTitle();
        this.content = entity.getPostContent();
        this.fileId = fileId;
    }
}
