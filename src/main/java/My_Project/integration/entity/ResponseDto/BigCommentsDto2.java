package My_Project.integration.entity.ResponseDto;

import My_Project.integration.entity.BigComments;
import My_Project.integration.entity.Dates;
import My_Project.integration.entity.Dto.UsersDto;
import My_Project.integration.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BigCommentsDto2 {
    private Long bigCommentsNumber;
    private UsersDto bigCommentedUser;
    private String content;
    private PostLikeAndDislikeResponseDto postLikeAndDislikeResponsedto;
    private Dates dates;

    private Long postCommentsNumber;

    public BigCommentsDto2(BigComments bigComments) {
        this.bigCommentsNumber = bigComments.getBigCommentsNumber();
        this.bigCommentedUser = new UsersDto(bigComments.getBigCommentedUser());
        this.content = bigComments.getContent();
        this.postLikeAndDislikeResponsedto = new PostLikeAndDislikeResponseDto(bigComments.getPostLikeAndDislike());
        this.dates = new Dates(bigComments.getDates().getUploadedTime(), bigComments.getDates().getUpdatedTime());
        this.postCommentsNumber = bigComments.getPostComments().getCommentNumber();
    }
}

