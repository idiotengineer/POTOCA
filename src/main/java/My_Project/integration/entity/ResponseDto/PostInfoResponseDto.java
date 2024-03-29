package My_Project.integration.entity.ResponseDto;

import My_Project.integration.entity.*;
import My_Project.integration.entity.Dto.PhotoDto;
import My_Project.integration.entity.Dto.UsersDto;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@Setter
@Getter
public class PostInfoResponseDto {

    private Long postNumber;

    private UsersDto postedUser;

    public String postTitle;

    public String postContent;

    public Dates dates;

    private Set<PhotoDto> photo;

    private Set<PostCommentsResponseDto2> comments = new HashSet<>();

    private PostLikeAndDislikeResponseDto postLikeAndDislike;

    private List<Integer> bestPostCommentsList = new ArrayList<>();

    private String dtype;

    private Long LikedCount = 0L;

    private Long point;

    private LocalDateTime closingTime;
    
    public PostInfoResponseDto(PostInfo postInfo1,List<PostComments> postComments, List<BigComments> bigComments) {
        this.setPostNumber(postInfo1.getPostNumber());
        this.setPostedUser(new UsersDto(postInfo1.getPostedUser()));
        this.setPostTitle(postInfo1.postTitle);
        this.setPostContent(postInfo1.postContent);
        this.setDates(new Dates(postInfo1.getDates().getUploadedTime(),postInfo1.getDates().getUpdatedTime()));

        this.setPhoto(
                postInfo1.getPhoto()
                        .stream().map(
                                PhotoDto::new
                        ).collect(Collectors.toSet())
        );

        this.setClosingTime(postInfo1.getClosingTime());
        this.setBestPostCommentsList(postInfo1.getBestPostCommentsList());
        this.setDtype(postInfo1.getDtype());
        this.setLikedCount(postInfo1.getLikedCount());
        this.setPoint(postInfo1.getPoint());
        this.setPostLikeAndDislike(new PostLikeAndDislikeResponseDto(postInfo1.getPostLikeAndDislike()));

        this.comments = postComments.stream()
                .map(
                        postComments1 -> new PostCommentsResponseDto2(postComments1,bigComments)
                ).collect(Collectors.toSet());
    }

    public boolean checkLike(String email) {
        return getPostLikeAndDislike().getLikedUser().stream()
                .anyMatch(
                        liked -> liked.getUsers().getEmail().equals(email)
                );
    }

    public boolean checkDisLike(String email) {
        return getPostLikeAndDislike().getDisLikedUser().stream()
                .anyMatch(
                        disLikedResponseDto -> disLikedResponseDto.getUsers().getEmail().equals(email)
                );
    }

    public String checkLikeAndDisLike(Optional<String> email) {
        if (email.isPresent()) {
            if (checkLike(email.get())) {
                return "likeChecked";
            } else if (checkDisLike(email.get())) {
                return "dislikeChecked";
            }
            return "noneChecked";
        }
        return "notLogin";
    }
}
