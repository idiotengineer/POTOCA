package My_Project.integration.entity.Dto;

import My_Project.integration.entity.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
public class PostDto {

    private Long postNumber;
    private Users users;
    private String postTitle;
    private String postContent;
    private Dates dates;
//    private List<Photo> images;
    private Set<Photo> images;
//    private List<PostComments> comments;
    private Set<PostComments> comments;
    private PostLikeAndDislike postLikeAndDislike;

    public PostDto(PostInfo postInfo) {
        this.postNumber = postInfo.getPostNumber();
        this.users = postInfo.getPostedUser();
        this.dates = postInfo.getDates();
        this.postTitle = postInfo.getPostTitle();
        this.postContent = postInfo.getPostContent();
//
//        this.images = new ArrayList<>(postInfo.getPhoto());
//        this.comments = new ArrayList<>(postInfo.getComments());
        this.images = new HashSet<>(postInfo.getPhoto());
        this.comments = new HashSet<>(postInfo.getComments());
        this.postLikeAndDislike = postInfo.getPostLikeAndDislike();
        this.images = postInfo.getPhoto();
//
//        Collections.copy(this.images, postInfo.getPhoto());
//        Collections.copy(this.comments,postInfo.getComments());
    }

    public PostDto(Long postNumber, Users users, String postTitle, String postContent, Dates dates, Set<Photo> images, Set<PostComments> comments, PostLikeAndDislike postLikeAndDislike) {
        this.postNumber = postNumber;
        this.users = users;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.dates = dates;
        this.images = images;
        this.comments = comments;
        this.postLikeAndDislike = postLikeAndDislike;
    }

    public PostDto() {
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
