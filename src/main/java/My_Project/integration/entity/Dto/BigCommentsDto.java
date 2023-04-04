package My_Project.integration.entity.Dto;

import My_Project.integration.entity.BigComments;
import My_Project.integration.entity.Dates;
import My_Project.integration.entity.PostLikeAndDislike;
import My_Project.integration.entity.ResponseDto.PostLikeAndDislikeDto;
import My_Project.integration.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BigCommentsDto {
    private Long bigCommentsNumber;
    private Users bigCommentedUser;
    private String content;
    private PostLikeAndDislikeDto postLikeAndDislikedto;
    private Dates dates;

    public BigCommentsDto(BigComments bigComments) {
        setBigCommentsNumber(bigComments.getBigCommentsNumber());
        setBigCommentedUser(bigComments.getBigCommentedUser());
        setContent(bigComments.getContent());
        setPostLikeAndDislikedto(new PostLikeAndDislikeDto(bigComments.getPostLikeAndDislike()));
        setDates(new Dates(bigComments.getDates().getUploadedTime(), bigComments.getDates().getUpdatedTime()));
    }
}
