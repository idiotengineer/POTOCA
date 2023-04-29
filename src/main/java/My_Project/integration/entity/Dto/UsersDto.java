package My_Project.integration.entity.Dto;

import My_Project.integration.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UsersDto {

    String email;

    String name;

    public UsersDto(Users users) {
        this.email = users.getEmail();
        this.name = users.getName();
    }
}
