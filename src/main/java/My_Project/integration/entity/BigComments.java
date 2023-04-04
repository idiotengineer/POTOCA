package My_Project.integration.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class BigComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "big_comments_number")
    private Long bigCommentsNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private Users bigCommentedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private PostComments postComments;

    @Column(name = "big_comments_content",nullable = false)
    private String content;

    @OneToOne(
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn
    private PostLikeAndDislike postLikeAndDislike;

    @Embedded
    private Dates dates;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private PostInfo postInfo;
}