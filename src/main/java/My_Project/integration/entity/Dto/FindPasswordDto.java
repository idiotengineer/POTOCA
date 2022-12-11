package My_Project.integration.entity.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FindPasswordDto {
    private String email;
    private String phone_number2;
}
