package My_Project.integration.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setPostInfo(PostInfo postInfo) {
        this.postInfo = postInfo;
        postInfo.setPostLikeAndDislike(this);
    }

    public void setLikedUser(Set<Users> LikedUser) {
        this.LikedUser = LikedUser;
    }

    public void setDisLikedUser(Set<Users> DisLikedUser) {
        this.DisLikedUser = DisLikedUser;
    }
}
