package My_Project.integration.entity.ResponseDto;

import My_Project.integration.entity.PostInfo;
import My_Project.integration.entity.PostLikeAndDislike;
import My_Project.integration.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostLikeAndDislikeDto {
    private Long id;
    private PostInfo postInfo;
    private Set<Users> LikedUser;
    private Set<Users> DisLikedUser;

    public PostLikeAndDislikeDto(PostLikeAndDislike postLikeAndDislike) {
        setId(postLikeAndDislike.getId());
        setPostInfo(postLikeAndDislike.getPostInfo());
        this.setLikedUser(clone(postLikeAndDislike.getLikedUser()));
        this.setDisLikedUser(clone(postLikeAndDislike.getDisLikedUser()));
//        Collections.copy(LikedUser,postLikeAndDislike.getLikedUser());
//        Collections.copy(DisLikedUser,postLikeAndDislike.getDisLikedUser());
    }

    public static<T> Set<T> clone(Set<T> original) {
        Set<T> copy = new HashSet<>();
        copy.addAll(original);
        return copy;
    }

}
