package My_Project.integration.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostLikeAndDislike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(
            fetch = FetchType.LAZY,
            mappedBy = "postLikeAndDislike")
    PostInfo postInfo;

    @OneToMany(
            mappedBy = "postLikeAndDislike"
    )
    private Set<Liked> liked = new HashSet<>();

    @OneToMany(
            mappedBy = "postLikeAndDislike"
    )
    private Set<DisLiked> disLiked = new HashSet<>();

    public void setPostInfo(PostInfo postInfo) {
        this.postInfo = postInfo;
        postInfo.setPostLikeAndDislike(this);
    }
}
