package My_Project.integration.entity;

import javax.persistence.*;

@Entity
public class BigComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "big_comments_number")
    private Long bigCommentsNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private Users bigCommentedUser;

    @Column(name = "big_comments_content",nullable = false)
    private String content;

    @Embedded
    private Dates dates;
}