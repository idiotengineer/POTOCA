package My_Project.integration.entity.ResponseDto;

import My_Project.integration.entity.PostInfo;
import My_Project.integration.entity.PostLikeAndDislike;
import My_Project.integration.entity.Users;
import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostLikeAndDislikeDto {
    private Long id;
    private PostInfo postInfo;
    private Set<Users> LikedUser = new HashSet<>();
    private Set<Users> DisLikedUser = new HashSet<>();

    public PostLikeAndDislikeDto(PostLikeAndDislike postLikeAndDislike) {
        setId(postLikeAndDislike.getId());
        setPostInfo(postLikeAndDislike.getPostInfo());

        if (!postLikeAndDislike.getLikedUser().isEmpty()) {
            this.setLikedUser(clone(postLikeAndDislike.getLikedUser()));
        }

        if (!postLikeAndDislike.getDisLikedUser().isEmpty()) {
            this.setDisLikedUser(clone(postLikeAndDislike.getDisLikedUser()));
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
