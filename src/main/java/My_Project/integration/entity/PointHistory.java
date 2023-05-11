package My_Project.integration.entity;

import My_Project.integration.entity.Enum.PlusOrMinus;
import My_Project.integration.entity.Enum.UsingType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="history_id")
    private Long historyId;

    @Enumerated(EnumType.STRING)
    private PlusOrMinus plusOrMinus;

    @Enumerated(EnumType.STRING)
    private UsingType usingType;

    @Column(name = "point_user_id")
    @JoinColumn(name = "id")
    private String userId;

    @Column(name = "price", nullable = false, updatable = false)
    private Long point;

    private String content;

    @Column(nullable = false)
    private LocalDateTime setUppedTime;
}
