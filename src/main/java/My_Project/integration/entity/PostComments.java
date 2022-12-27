package My_Project.integration.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class PostComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_comments_number")
    private Long commentNumber;

    @Column(name = "post_commented_users_email")
    private String postCommentedUsersEmail;

    @Column(name = "post_comments_contents", nullable = false)
    private String postCommentsContents;

    @Embedded
    private Dates dates;

    @OneToMany
    @JoinColumn(name = "post_comments_number")
    private List<BigComments> bigCommentsList = new ArrayList<>();
}