package My_Project.integration.entity.Dto;

import My_Project.integration.entity.*;
import My_Project.integration.entity.ResponseDto.PostLikeAndDislikeDto;
import lombok.*;

import java.util.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
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
    private PostLikeAndDislikeDto postLikeAndDislikeDto;

    private List<Integer> bestPostCommentsList;

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
        this.postLikeAndDislikeDto = new PostLikeAndDislikeDto(postInfo.getPostLikeAndDislike());
        this.images = postInfo.getPhoto();
        this.bestPostCommentsList = postInfo.getBestPostCommentsList();
//
//        Collections.copy(this.images, postInfo.getPhoto());
//        Collections.copy(this.comments,postInfo.getComments());
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
//        return getPostLikeAndDislikeDto().getLikedUser().stream()
//                .anyMatch(users1 -> users1.equals(users));
        return getPostLikeAndDislikeDto().getLikedUser().stream()
                .anyMatch(
                        liked -> liked.getUsers().equals(users)
                );
    }

    public boolean checkDisLike(Users users) {
//        return getPostLikeAndDislikeDto().getDisLikedUser().stream()
//                .anyMatch(users1 -> users1.equals(users));

        return getPostLikeAndDislikeDto().getDisLikedUser().stream()
                .anyMatch(
                        disLiked -> disLiked.getUsers().equals(users)
                );
    }

    public PostDto(PostInfo postInfo, PostLikeAndDislikeDto postLikeAndDislikeDto) {
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
        this.postLikeAndDislikeDto = postLikeAndDislikeDto;
        this.bestPostCommentsList = postInfo.getBestPostCommentsList();
    }
}
