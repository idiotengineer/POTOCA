package My_Project.integration.entity.ResponseDto;

import My_Project.integration.entity.Dto.UsersDto;
import My_Project.integration.entity.Liked;
import My_Project.integration.entity.PostLikeAndDislike;
import My_Project.integration.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LikedResponseDto {

    private Long id;
    private UsersDto users;

    public LikedResponseDto(Liked liked) {
        this.id = liked.getId();
        this.users = new UsersDto(liked.getUsers());
    }
}
