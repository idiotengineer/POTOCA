package My_Project.integration.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="history_id")
    private Long historyId;

    @Column(name = "point_user_id")
    @JoinColumn(name = "id")
    private String userId;

    @Column(name = "price", nullable = false, updatable = false)
    private Long price;

    @Column(nullable = false)
    private Dates setUppedTime;
}
