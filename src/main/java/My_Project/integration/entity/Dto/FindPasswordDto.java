package My_Project.integration.entity.Dto;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FindPasswordDto {
    @ApiParam(value = "이메일", required = true)
    private String email;

    @ApiParam(value = "휴대전화 번호", required = true)
    private String phone_number2;
}
