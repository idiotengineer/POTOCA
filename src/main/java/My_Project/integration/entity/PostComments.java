package My_Project.integration.entity;

import My_Project.integration.entity.Dto.CommentDto;
import My_Project.integration.entity.ResponseDto.PostCommentsResponseDto;
import My_Project.integration.entity.ResponseDto.PostLikeAndDislikeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
            mappedBy = "bigCommentsNumber",
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
        this.bigCommentsList = new ArrayList<>();
        this.postLikeAndDislike = new PostLikeAndDislike();

        Dates dates1 = new Dates(LocalDateTime.now(),LocalDateTime.now());
        this.dates = dates1;
    }

    public Integer bigCommentSize() {
        return getBigCommentsList().size();
    }

    public void addBigComments(BigComments bigComments) {
        this.getBigCommentsList().add(bigComments);
        bigComments.setPostComments(this);
    }
}