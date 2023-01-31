package My_Project.integration.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostLikeAndDislike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn
    private List<Users> LikedUser = new ArrayList<>();

    @OneToMany
    @JoinColumn
    private List<Users> DisLikedUser = new ArrayList<>();
}
