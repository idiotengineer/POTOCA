package My_Project.integration.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Liked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private PostLikeAndDislike postLikeAndDislike;
}
