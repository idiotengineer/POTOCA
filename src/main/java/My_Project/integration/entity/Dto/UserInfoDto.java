package My_Project.integration.entity.Dto;

import My_Project.integration.entity.PointHistory;
import My_Project.integration.entity.PostInfo;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserInfoDto {


    @ApiParam(value = "이메일", required = true)
    private String email;

    @ApiParam(value = "비밀번호", required = true)
    private String password;

    @ApiParam(value = "이름", required = true)
    private String name;

    @ApiParam(value = "휴대전화 번호", required = true)
    private String phone_number;

    @ApiParam(value = "주민등록번호", required = true)
    private String ssn;


    @ApiParam(value = "도시명", required = true)
    private String city_name;

    @ApiParam(value = "읍/면/동 명")
    private String town_name;

    @ApiParam(value = "도로명", required = true)
    private String street_name;

    @ApiParam(value = "우편번호")
    private String zip_code;

    @ApiParam(value = "상세주소", required = true)
    private String details;

    public UserInfoDto(String Email, String password, String name, String phone_number, String ssn, String cityName, String townName, String streetName, String zipCode, String detailsCode) {
        this.email = Email;
        this.password = password;
        this.name = name;
        this.phone_number = phone_number;
        this.ssn = ssn;
        this.city_name = city_name;
        this.town_name = townName;
        this.street_name = streetName;
        this.zip_code = zipCode;
        this.details = detailsCode;
    }

    public UserInfoDto() {
    }
}
