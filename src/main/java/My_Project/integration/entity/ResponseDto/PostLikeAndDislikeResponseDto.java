package My_Project.integration.entity.ResponseDto;

import My_Project.integration.entity.PostLikeAndDislike;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostLikeAndDislikeResponseDto {

    private Long id;
    private Set<LikedResponseDto> LikedUser = new HashSet<>();
    private Set<DisLikedResponseDto> DisLikedUser = new HashSet<>();

    public PostLikeAndDislikeResponseDto(PostLikeAndDislike postLikeAndDislike) {
        this.LikedUser = postLikeAndDislike.getLiked()
                .stream().map(
                        LikedResponseDto::new
                ).collect(Collectors.toSet());

        this.DisLikedUser = postLikeAndDislike.getDisLiked()
                .stream().map(
                        DisLikedResponseDto::new
                ).collect(Collectors.toSet());

        this.id = postLikeAndDislike.getId();
    }
}
