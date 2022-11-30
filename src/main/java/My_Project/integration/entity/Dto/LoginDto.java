package My_Project.integration.entity.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginDto {
    private String id;
    private String password;
}
