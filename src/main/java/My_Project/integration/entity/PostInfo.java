package My_Project.integration.entity;

import My_Project.integration.entity.Dto.PostInfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class PostInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_number")
    private Long postNumber;

    @ManyToOne
    @JoinColumn(name = "id")
    private Users postedUser;

    @Column(name = "post_title", length = 50, nullable = false)
    public String postTitle;

    @Column(name = "post_content", nullable = false)
    public String postContent;

    @Embedded
    public Dates dates;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<Byte> image;

    @OneToMany
    @JoinColumn(name = "id")
    private List<PostComments> comments;

    public PostInfo(Users users, PostInfoDto postInfo) {
        Dates dates = new Dates(
                LocalDateTime.now(),LocalDateTime.now()
        );

        this.postedUser = users;
        this.postTitle = postInfo.getPostTitle();
        this.postContent = postInfo.getPostContent();
        this.dates = dates;
        this.comments = new ArrayList<>();
        this.image = new ArrayList<>(postInfo.getImage().size());
        Collections.copy(this.image,postInfo.getImage());
    }
}

