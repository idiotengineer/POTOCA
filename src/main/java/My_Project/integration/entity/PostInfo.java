package My_Project.integration.entity;

import My_Project.integration.entity.Dto.PostInfoDto;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Builder
public class PostInfo{

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
    private Set<PostComments> comments = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn
    private PostLikeAndDislike postLikeAndDislike;

    @ElementCollection
    private List<Integer> bestPostCommentsList = new ArrayList<>(3);

    @Column(insertable = false, updatable = false)
    private String dtype;

    private Long LikedCount = 0L;

    private Long point;

    private Boolean closed = false;

    private LocalDateTime closingTime;
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
        this.dtype = postInfo.getDtype();
        this.point = postInfo.getPoint();
        this.closingTime = LocalDateTime.now().plusHours(postInfo.getClosingTime());
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

    public void setPostInfoWithNoArgsConstructor(Users users, PostInfoDto postInfoDto, PostLikeAndDislike postLikeAndDislike) {
        Dates dates = new Dates(LocalDateTime.now(), LocalDateTime.now());

        setPostedUser(users);
        setPostLikeAndDislike(postLikeAndDislike);
        setDates(dates);
        setPostTitle(postInfoDto.getPostTitle());
        setPostContent(postInfoDto.getPostContent());
        setComments(new HashSet<>());
        setPoint(postInfoDto.getPoint());
        setClosingTime(LocalDateTime.now().plusHours(postInfoDto.getClosingTime()));

        this.bestPostCommentsList.add(0);
        this.bestPostCommentsList.add(0);
        this.bestPostCommentsList.add(0);
    }

    public void updateTop3LikeCount() {
        List<Integer> collect = comments
                .stream()
                .map(
                        postComments -> postComments.getPostLikeAndDislike().getLiked().size()
                )
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .collect(Collectors.toList());


        if (collect.size() < 2) {
            collect.add(0);
        }

        if (collect.size() < 3) {
            collect.add(0);
        }

        this.bestPostCommentsList.clear();
        this.bestPostCommentsList.addAll(collect);
    }
}

