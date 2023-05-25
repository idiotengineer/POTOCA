package My_Project.integration.entity;


import My_Project.integration.entity.Dto.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"ssn","phone_number"})})
public class Users {

    @Column(name = "email", length = 40, updatable = false, nullable = false)
    @Id
    private String email;

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

    @OneToMany(mappedBy = "postedUser", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<PostInfo> uploadedPost = new HashSet<>();

    @OneToMany(mappedBy = "userId", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<PointHistory> pointHistories = new HashSet<>();

    @Embedded
    private Dates dates;

    @OneToMany(
            mappedBy = "users",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Liked> liked = new HashSet<>();

    @OneToMany(
            mappedBy = "users",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<DisLiked> disLiked = new HashSet<>();


    public Users(UserInfoDto userInfoDto){
        this.setEmail(userInfoDto.getEmail());
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

        Set<PostInfo> postInfoList = new HashSet<>();
        Set<PointHistory> pointHistoryList = new HashSet<>();

        this.setUploadedPost(postInfoList);
        this.setPointHistories(pointHistoryList);

        Dates dates = new Dates(LocalDateTime.now(), LocalDateTime.now());

        this.setDates(dates);
    }

}
