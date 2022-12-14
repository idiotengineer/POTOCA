package My_Project.integration.entity.Dto;

import My_Project.integration.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Long postNumber;
    private Users users;
    private String postTitle;
    private String postContent;
    private Dates dates;
    private List<Photo> images;
    private List<PostComments> comments;


    public PostDto(PostInfo postInfo) {
        this.postNumber = postInfo.getPostNumber();
        this.users = postInfo.getPostedUser();
        this.dates = postInfo.getDates();
        this.postTitle = postInfo.getPostTitle();
        this.postContent = postInfo.getPostContent();

        this.images = new ArrayList<>(postInfo.getPhoto());
        this.comments = new ArrayList<>(postInfo.getComments());

        Collections.copy(this.images, postInfo.getPhoto());
        Collections.copy(this.comments,postInfo.getComments());
    }
}
