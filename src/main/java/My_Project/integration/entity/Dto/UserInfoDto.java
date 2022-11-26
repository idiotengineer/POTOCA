package My_Project.integration.entity.Dto;

import My_Project.integration.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class UserInfoDto {

    private String id;
    private String password;
    private String name;
    private String phoneNumber;
    private String ssn;

    private Address address;
    private Long point;
    private List<PointHistory> pointHistories;
    private List<PostInfo> postInfos;

    public UserInfoDto(String id, String password, String name, String phoneNumber, String ssn, Address address, Long point, List<PointHistory> pointHistories, List<PostInfo> postInfos) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.ssn = ssn;
        this.address = address;
        this.point = point;
        this.pointHistories = pointHistories;
        this.postInfos = postInfos;

        Address address = new Address(userInfoDto.getCityName(),
                userInfoDto.getTownName(),
                userInfoDto.getStreetName(),
                userInfoDto.getZipCode(),
                userInfoDto.getDetailsCode());

        List<PointHistory> pointHistoryList = new ArrayList<>();
        List<PostInfo> postInfoList = new ArrayList<>();

        Users users = new Users(
                userInfoDto.getId(),
                userInfoDto.getPassword(),
                userInfoDto.getName(),
                userInfoDto.getPhoneNumber(),
                userInfoDto.getSsn(),
                address,
                0,
                pointHistoryList,
                postInfoList
        );

    }
}
