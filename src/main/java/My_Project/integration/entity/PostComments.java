package My_Project.integration.entity;

import My_Project.integration.entity.Dto.CommentDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class PostComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_comments_number")
    private Long commentNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private PostInfo postInfo;

    @Column(name = "post_commented_users_email")
    private String postCommentedUsersEmail;

    @Column(name = "post_comments_contents", nullable = false)
    private String postCommentsContents;

    @Embedded
    private Dates dates;

    @OneToMany(
            mappedBy = "postComments",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<BigComments> bigCommentsList = new ArrayList<>();

    @OneToOne(
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn
    private PostLikeAndDislike postLikeAndDislike;

    public PostComments(CommentDto commentDto,PostInfo postInfo,PostLikeAndDislike postLikeAndDislike) {
        this.postInfo = postInfo;
        this.postCommentedUsersEmail = commentDto.getUsers_email();
        this.postCommentsContents = commentDto.getComment();
        this.postLikeAndDislike = postLikeAndDislike;

        Dates dates1 = new Dates(LocalDateTime.now(),LocalDateTime.now());
        this.dates = dates1;
    }

    public PostComments(Long commentNumber, PostInfo postInfo, String postCommentedUsersEmail, String postCommentsContents, Dates dates, List<BigComments> bigCommentsList, PostLikeAndDislike postLikeAndDislike) {
        this.commentNumber = commentNumber;
        this.postInfo = postInfo;
        this.postCommentedUsersEmail = postCommentedUsersEmail;
        this.postCommentsContents = postCommentsContents;
        this.dates = dates;
        this.bigCommentsList = new ArrayList<>(bigCommentsList);
        this.postLikeAndDislike = postLikeAndDislike;
    }

    public PostComments() {
    }

    public Integer bigCommentSize() {
        return getBigCommentsList().size();
    }

    public void addBigComments(BigComments bigComments) {
        this.bigCommentsList.add(bigComments);
        bigComments.setPostComments(this);
    }
}