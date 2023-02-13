package My_Project.integration.entity.Dto;

import My_Project.integration.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    private PostLikeAndDislike postLikeAndDislike;

    public PostDto(PostInfo postInfo) {
        this.postNumber = postInfo.getPostNumber();
        this.users = postInfo.getPostedUser();
        this.dates = postInfo.getDates();
        this.postTitle = postInfo.getPostTitle();
        this.postContent = postInfo.getPostContent();

        this.images = new ArrayList<>(postInfo.getPhoto());
        this.comments = new ArrayList<>(postInfo.getComments());
        this.postLikeAndDislike = postInfo.getPostLikeAndDislike();
        this.images = postInfo.getPhoto();

        Collections.copy(this.images, postInfo.getPhoto());
        Collections.copy(this.comments,postInfo.getComments());
    }

    public String checkLikeAndDisLike(Optional<Users> users) {
        if (users.isPresent()) {
            if (checkLike(users.get())) {
                return "likeChecked";
            } else if (checkDisLike(users.get())) {
                return "dislikeChecked";
            }
            return "noneChecked";
        }
        return "notLogin";
    }

    public boolean checkLike(Users users) {
        return getPostLikeAndDislike().getLikedUser().stream()
                .anyMatch(users1 -> users1.equals(users));
    }

    public boolean checkDisLike(Users users) {
        return getPostLikeAndDislike().getDisLikedUser().stream()
                .anyMatch(users1 -> users1.equals(users));
    }
}
