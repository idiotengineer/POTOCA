package My_Project.integration.entity.Dto;

import My_Project.integration.entity.PointHistory;
import My_Project.integration.entity.PostInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserInfoDto {

    private String id;
    private String password;
    private String name;
    private String phone_number;
    private String ssn;

    private String city_name;
    private String town_name;
    private String street_name;
    private String zip_code;
    private String details;

    public UserInfoDto(String id, String password, String name, String phone_number, String ssn, String cityName, String townName, String streetName, String zipCode, String detailsCode) {
        this.id = id;
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
