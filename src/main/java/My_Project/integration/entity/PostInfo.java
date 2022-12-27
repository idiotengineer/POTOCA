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

    @OneToMany(
            mappedBy = "postInfo",
            orphanRemoval = true,
            cascade= {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private List<Photo> photo = new ArrayList<>();

    @OneToMany(
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    @JoinColumn(name = "id")
    private List<PostComments> comments;



    // Board에서 파일 처리 위함
    public void addPhoto(Photo photo) {
        this.photo.add(photo);

        // 게시글에 파일이 저장되어있지 않은 경우
        if(photo.getPostInfo() != this)
            // 파일 저장
            photo.setPostInfo(this);
    }

    public PostInfo(Users users, PostInfoDto postInfo) {
        Dates dates = new Dates(
                LocalDateTime.now(),LocalDateTime.now()
        );

        this.postedUser = users;
        this.postTitle = postInfo.getPostTitle();
        this.postContent = postInfo.getPostContent();
        this.dates = dates;
        this.comments = new ArrayList<>();
    }
}

