package My_Project.integration.entity;

import My_Project.integration.entity.Dto.PostInfoDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class PostInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_number")
    private Long postNumber;

    @ManyToOne(fetch = FetchType.LAZY)
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
            cascade = CascadeType.ALL
    )
    @BatchSize(size = 100)
//    private List<Photo> photo = new ArrayList<>();
    private Set<Photo> photo = new HashSet<>();

    @OneToMany(
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            mappedBy = "postInfo"
    )
    @BatchSize(size = 1000)
    @JsonIgnore
    private Set<PostComments> comments;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn
    @JsonIgnore
    private PostLikeAndDislike postLikeAndDislike;

    @ElementCollection
    private List<Integer> bestPostCommentsList = new ArrayList<>(3);

    public PostInfo(Long postNumber, Users postedUser, String postTitle, String postContent, Dates dates, Set<Photo> photo, Set<PostComments> comments, PostLikeAndDislike postLikeAndDislike) {
        this.postNumber = postNumber;
        this.postedUser = postedUser;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.dates = dates;
        this.photo = photo;
        this.comments = comments;
        this.postLikeAndDislike = postLikeAndDislike;

        this.bestPostCommentsList.add(0,1);
        this.bestPostCommentsList.add(1,2);
        this.bestPostCommentsList.add(2,3);
    }

    public PostInfo() {
        this.bestPostCommentsList.add(0,1);
        this.bestPostCommentsList.add(1,2);
        this.bestPostCommentsList.add(2,3);
    }

    // Board에서 파일 처리 위함
    public void addPhoto(Photo photo) {
        this.photo.add(photo);

        // 게시글에 파일이 저장되어있지 않은 경우
        if(photo.getPostInfo() != this)
            // 파일 저장`
            photo.setPostInfoByPostInfo(this);
    }

    public PostInfo(Users users, PostInfoDto postInfo,PostLikeAndDislike postLikeAndDislike) {
        Dates dates = new Dates(
                LocalDateTime.now(),LocalDateTime.now()
        );
        this.postedUser = users;
        this.postTitle = postInfo.getPostTitle();
        this.postContent = postInfo.getPostContent();
        this.dates = dates;
        this.comments = new HashSet<>();
        this.postLikeAndDislike = postLikeAndDislike;

        this.bestPostCommentsList.add(0,1);
        this.bestPostCommentsList.add(1,2);
        this.bestPostCommentsList.add(2,3);
    }

    public void addComments(PostComments postComments) {
        this.comments.add(postComments);
        postComments.setPostInfo(this);
    }

    public void setPostCommentsList(Set<PostComments> postComments) {
        this.comments = postComments;
        for (PostComments postComment : postComments) {
            postComment.setPostInfo(this);
        }
    }
}

