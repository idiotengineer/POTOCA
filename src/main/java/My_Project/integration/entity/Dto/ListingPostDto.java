package My_Project.integration.entity.Dto;

import My_Project.integration.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ListingPostDto {

        private Long postNumber;
        private Users users;
        private String postTitle;
        private String postContent;
        private Dates dates;
        private PostLikeAndDislike postLikeAndDislike;

        public ListingPostDto(PostInfo postInfo) {
            this.postNumber = postInfo.getPostNumber();
            this.users = postInfo.getPostedUser();
            this.dates = postInfo.getDates();
            this.postTitle = postInfo.getPostTitle();
            this.postContent = postInfo.getPostContent();
            this.postLikeAndDislike = postInfo.getPostLikeAndDislike();
        }

        public String checkLikeAndDisLike(Optional<Users> users) {
            if (users.isPresent()) {
                if (checkLike(users.get())) {
                    return "likeChecked";
                } else if (checkDisLike(users.get())) {
                    return "dislikeChecked";
                }
                return "noneChecked";
            }
            return "notLogin";
        }

        public boolean checkLike(Users users) {
            return getPostLikeAndDislike().getLikedUser().stream()
                    .anyMatch(users1 -> users1.equals(users));
        }

        public boolean checkDisLike(Users users) {
            return getPostLikeAndDislike().getDisLikedUser().stream()
                    .anyMatch(users1 -> users1.equals(users));
        }
    }

