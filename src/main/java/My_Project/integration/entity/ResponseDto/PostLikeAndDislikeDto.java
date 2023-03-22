package My_Project.integration.entity.ResponseDto;

import My_Project.integration.entity.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostLikeAndDislikeDto {
    private Long id;
    private PostInfo postInfo;
    private Set<Liked> LikedUser = new HashSet<>();
    private Set<DisLiked> DisLikedUser = new HashSet<>();

    public PostLikeAndDislikeDto(PostLikeAndDislike postLikeAndDislike) {
        setId(postLikeAndDislike.getId());
        setPostInfo(postLikeAndDislike.getPostInfo());

        if (!postLikeAndDislike.getLiked().isEmpty()) {
            this.setLikedUser(clone(postLikeAndDislike.getLiked()));
        }

        if (!postLikeAndDislike.getDisLiked().isEmpty()) {
            this.setDisLikedUser(clone(postLikeAndDislike.getDisLiked()));
        }
//        Collections.copy(LikedUser,postLikeAndDislike.getLikedUser());
//        Collections.copy(DisLikedUser,postLikeAndDislike.getDisLikedUser());
    }

    public static<T> Set<T> clone(Set<T> original) {
        Set<T> copy = new HashSet<>();
        copy.addAll(original);
        return copy;
    }


}
