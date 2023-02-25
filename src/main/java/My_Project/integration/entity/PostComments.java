package My_Project.integration.entity;

import My_Project.integration.entity.Dto.CommentDto;
import My_Project.integration.entity.ResponseDto.PostLikeAndDislikeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_comments_number")
    private Long commentNumber;

    @Column(name = "post_number")
    private Long postNumber;

    @Column(name = "post_commented_users_email")
    private String postCommentedUsersEmail;

    @Column(name = "post_comments_contents", nullable = false)
    private String postCommentsContents;

    @Embedded
    private Dates dates;

    @OneToMany
    @JoinColumn(name = "post_comments_number")
    private List<BigComments> bigCommentsList;

    @OneToOne(
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn
    private PostLikeAndDislike postLikeAndDislike;

    public PostComments(CommentDto commentDto) {
        this.postNumber = commentDto.getPost_number();
        this.postCommentedUsersEmail = commentDto.getUsers_email();
        this.postCommentsContents = commentDto.getComment();
        this.bigCommentsList = new ArrayList<>();
        this.postLikeAndDislike = new PostLikeAndDislike();

        Dates dates1 = new Dates(LocalDateTime.now(),LocalDateTime.now());
        this.dates = dates1;
    }
}