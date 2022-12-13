package My_Project.integration.entity.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FindEmailDto {

    @ApiParam(value = "휴대전화 번호", required = true)
    private String phone_number;

    @ApiParam(value = "사용자 이름", required = true)
    private String name;
}
