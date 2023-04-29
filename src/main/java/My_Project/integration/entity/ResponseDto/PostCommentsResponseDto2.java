package My_Project.integration.entity.ResponseDto;

import My_Project.integration.entity.BigComments;
import My_Project.integration.entity.Dates;
import My_Project.integration.entity.Dto.BigCommentsDto;
import My_Project.integration.entity.PostComments;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostCommentsResponseDto2 {

    private Long commentNumber;

    private String postCommentedUsersEmail;

    private String postCommentsContents;

    private Dates dates;

    private List<BigCommentsDto2> bigCommentsList = new ArrayList<>();

    private PostLikeAndDislikeResponseDto postLikeAndDislike;


    public PostCommentsResponseDto2(PostComments postComments,List<BigComments> bigComments) {
        this.commentNumber = postComments.getCommentNumber();
        this.postCommentedUsersEmail = postComments.getPostCommentedUsersEmail();
        this.postCommentsContents = postComments.getPostCommentsContents();
        this.dates = (new Dates(
                postComments.getDates().getUploadedTime(),
                postComments.getDates().getUpdatedTime()
        ));

        this.postLikeAndDislike = new PostLikeAndDislikeResponseDto(postComments.getPostLikeAndDislike());


        bigComments
                .stream()
                        .map(
                                BigCommentsDto2::new
                        ).collect(Collectors.toList())
                .stream()
                .forEach(
                        (bigCommentsDto2) -> {
                            if(bigCommentsDto2
                                    .getPostCommentsNumber()
                                    .equals(commentNumber)){
                                bigCommentsList.add(bigCommentsDto2);
                            }
                        }
                );

    }
}
