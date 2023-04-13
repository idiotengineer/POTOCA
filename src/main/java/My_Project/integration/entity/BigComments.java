package My_Project.integration.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Builder
@Setter
public class BigComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "big_comments_number")
    private Long bigCommentsNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users")
    private Users bigCommentedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_comments_number")
    private PostComments postComments;

    @Column(name = "big_comments_content",nullable = false)
    private String content;

    @OneToOne(
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name="lidi")
    private PostLikeAndDislike postLikeAndDislike;

    @Embedded
    private Dates dates;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="postInfo")
    private PostInfo postInfo;

    public BigComments(Long bigCommentsNumber, Users bigCommentedUser, PostComments postComments, String content, PostLikeAndDislike postLikeAndDislike, Dates dates, PostInfo postInfo) {
        this.bigCommentsNumber = bigCommentsNumber;
        this.bigCommentedUser = bigCommentedUser;
        postComments.addBigComments(this);
        this.content = content;
        this.postLikeAndDislike = postLikeAndDislike;
        this.dates = dates;
        this.postInfo = postInfo;
    }

    public BigComments() {
    }
}