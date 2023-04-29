package My_Project.integration.entity.ResponseDto;

import My_Project.integration.entity.DisLiked;
import My_Project.integration.entity.Dto.UsersDto;
import My_Project.integration.entity.Liked;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DisLikedResponseDto {

    private Long id;
    private UsersDto users;

    public DisLikedResponseDto(DisLiked disLiked) {
        this.id = disLiked.getId();
        this.users = new UsersDto(disLiked.getUsers());
    }
}
