package My_Project.integration.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private PostInfo postInfo;

    @Column(nullable = false)
    private String origFileName;

    @Column(nullable = false)
    private String filePath;

    private Long fileSize;

    @Builder
    public Photo(String orgFileName, String filePath, Long fileSize) {
        this.origFileName = orgFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    public void setPostInfo(PostInfo postInfo) {
        this.postInfo = postInfo;
        // 게시글에 현재 파일이 존재하지 않는다면
        if (!postInfo.getPhoto().contains(this))
            // 파일 추가
            postInfo.getPhoto().add(this);
    }
}
