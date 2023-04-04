package My_Project.integration.entity.Dto;

import My_Project.integration.entity.Dates;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class addBigCommentsDto {

    private Long post_number;
    private Long comment_number;
    private String comment;
}
