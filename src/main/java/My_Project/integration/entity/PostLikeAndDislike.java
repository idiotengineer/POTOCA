package My_Project.integration.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostLikeAndDislike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(
            fetch = FetchType.LAZY,
            mappedBy = "postLikeAndDislike")
    PostInfo postInfo;

    @OneToMany
    @JoinColumn
    private Set<Users> LikedUser = new HashSet<>();

    @OneToMany
    @JoinColumn
    private Set<Users> DisLikedUser = new HashSet<>();
}
