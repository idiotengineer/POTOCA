package My_Project.integration.entity.ResponseDto;

import My_Project.integration.entity.BigComments;
import My_Project.integration.entity.Dates;
import My_Project.integration.entity.Dto.BigCommentsDto;
import My_Project.integration.entity.PostComments;
import My_Project.integration.entity.PostLikeAndDislike;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentsResponseDto {

    private Long commentNumber;

    private Long postNumber;

    private String postCommentedUsersEmail;

    private String postCommentsContents;

    private Dates dates;

    private List<BigCommentsDto> bigCommentsList;

    private PostLikeAndDislikeDto postLikeAndDislike;

    public PostCommentsResponseDto(PostComments postComments) {
        setCommentNumber(postComments.getCommentNumber());
        setPostNumber(postComments.getPostInfo().getPostNumber());
        setPostCommentedUsersEmail(postComments.getPostCommentedUsersEmail());
        setPostCommentsContents(postComments.getPostCommentsContents());
        setDates(new Dates(
                postComments.getDates().getUploadedTime(),
                postComments.getDates().getUpdatedTime()
        ));

        setBigCommentsList(postComments.getBigCommentsList()
                .stream()
                .map(
                        BigCommentsDto::new
                ).collect(Collectors.toList())
        );

        setPostLikeAndDislike(
                new PostLikeAndDislikeDto(postComments.getPostLikeAndDislike())
        );
    }

    public void setDates(Dates dates) {
        this.dates = dates;
    }
}
