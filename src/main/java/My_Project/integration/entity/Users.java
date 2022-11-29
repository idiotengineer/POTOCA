package My_Project.integration.entity;


import My_Project.integration.entity.Dto.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"ssn","phone_number"})})
public class Users {

    @Column(name = "id", length = 40, updatable = false, nullable = false)
    @Id
    private String id;

    @Column(name = "password", length = 20, nullable = false)
    private String password;

    @Column(name = "name", length = 25, nullable = false)
    private String name;

    @Column(name = "phone_number", length = 12, nullable = false)
    private String phoneNumber;

    @Column(name =  "ssn", length = 13, nullable = false, updatable = false)
    private String ssn;

    @Embedded
    private Address address;

    @Column(name = "point")
    private Long point;

    @OneToMany(mappedBy = "postedUser")
    private List<PostInfo> uploadedPost = new ArrayList<>();

    @OneToMany(mappedBy = "userId")
    private List<PointHistory> pointHistories = new ArrayList<>();

    @Embedded
    private Dates dates;

    public Users(UserInfoDto userInfoDto){
        this.setId(userInfoDto.getId());
        this.setPassword(userInfoDto.getPassword());
        this.setPhoneNumber(userInfoDto.getPhone_number());
        this.setSsn(userInfoDto.getSsn());
        this.setPoint(0L);
        this.setName(userInfoDto.getName());

        Address address = new Address(
                userInfoDto.getCity_name(),
                userInfoDto.getTown_name(),
                userInfoDto.getStreet_name(),
                userInfoDto.getZip_code(),
                userInfoDto.getDetails()
        );

        this.setAddress(address);

        List<PostInfo> postInfoList = new ArrayList<>();
        List<PointHistory> pointHistoryList = new ArrayList<>();

        this.setUploadedPost(postInfoList);
        this.setPointHistories(pointHistoryList);

        Dates dates = new Dates(LocalDateTime.now(), LocalDateTime.now());

        this.setDates(dates);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public void setUploadedPost(List<PostInfo> uploadedPost) {
        this.uploadedPost = uploadedPost;
    }

    public void setPointHistories(List<PointHistory> pointHistories) {
        this.pointHistories = pointHistories;
    }

    public void setDates(Dates dates) {
        this.dates = dates;
    }
}
