package My_Project.integration.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
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

    @ElementCollection(fetch = FetchType.LAZY)
    private List<Byte> image = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "id")
    private List<PostComments> comments = new ArrayList<>();
}

