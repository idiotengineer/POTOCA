package My_Project.integration.entity.Dto;

import My_Project.integration.entity.Dates;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long post_number;
    private String users_email;
    private String comment;

}
