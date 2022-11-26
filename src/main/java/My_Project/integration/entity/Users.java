package My_Project.integration.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"ssn","phone_number"})})
public class Users {

    @Column(name = "id", length = 40, updatable = false)
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
}
