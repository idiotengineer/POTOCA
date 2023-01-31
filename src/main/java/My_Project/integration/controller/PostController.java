package My_Project.integration.controller;

import My_Project.integration.entity.Dto.CommentDto;
import My_Project.integration.entity.Dto.PostDto;
import My_Project.integration.entity.Dto.PostInfoDto;
import My_Project.integration.entity.ResponseDto.PhotoResponseDto;
import My_Project.integration.entity.Users;
import My_Project.integration.service.PhotoService;
import My_Project.integration.service.PostService;
import My_Project.integration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    PhotoService photoService;

    @Autowired
    UserService userService;

    @PostMapping("/post_execute2")
    public String create(
            @RequestPart(value = "image", required = false) List<MultipartFile> files,
            PostInfoDto postInfoDto,
            @CookieValue(name = "users") Cookie cookie,
            RedirectAttributes model) {
        try {
            PostDto postDto = postService.create(postInfoDto, files, cookie);
            // PostDto 내부에는 private List<PostComments> comments까지 구현되어있음.

            List<PhotoResponseDto> photoResponseDtoList = photoService.findAllByPostInfo(postDto.getPostNumber());
            List<String> photoPath = new ArrayList<>();

/*
            File file = new File(".");
            String projectPath = file.getAbsolutePath();
            String subStringProjectPath = projectPath.substring(0, projectPath.length() - 1);
*/

            for (PhotoResponseDto photoResponseDto : photoResponseDtoList) {
                photoPath.add(photoResponseDto.getFilePath());
            }

            model.addAttribute("time", LocalDateTime.now());
            model.addAttribute("photoPath", photoPath);
            model.addAttribute("postDtoEmail", postDto.getUsers().getEmail());
            model.addAttribute("postDtoGetPostContent", postDto.getPostContent());
            model.addFlashAttribute("postDto", postDto);
            return "redirect:/post";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("string", "일반 게시글 작성에 실패하셨습니다");
            return "redirect:/alert";
        }
    }

    @RequestMapping(value = "/register_comments", method = {RequestMethod.POST, RequestMethod.GET})
    public String registerComments(
            @RequestBody CommentDto commentDto,
            @CookieValue(name = "users") Optional<Cookie> cookie,
            RedirectAttributes attr) {
        try {
            postService.registerComments(commentDto, cookie);
            attr.addAttribute("id", commentDto.getPost_number());
            return "redirect:/find_post";
        } catch (Exception e) {
            e.printStackTrace();
            attr.addAttribute("string", "로그인 후 댓글을 작성 해 주세요");
            return "redirect:/alert";
        }
    }


/*
        @PostMapping("/post_execute")
        public String create1(
                @RequestPart(value = "image", required = false) List<MultipartFile> files,
                PostInfoDto postInfoDto,
                @CookieValue(name = "users") Cookie cookie,
                RedirectAttributes model) {
            try {
                PostDto postDto = postService.create(postInfoDto, files, cookie);
                // PostDto 내부에는 private List<PostComments> comments까지 구현되어있음.

                List<PhotoResponseDto> photoResponseDtoList = photoService.findAllByPostInfo(postDto.getPostNumber());
                List<String> photoPath = new ArrayList<>();

                for (PhotoResponseDto photoResponseDto : photoResponseDtoList) {
                    photoPath.add(photoResponseDto.getFilePath());
                }

                model.addAttribute("time",LocalDateTime.now());
                model.addFlashAttribute("postDto",postDto);
                return "redirect:/post";
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("string","일반 게시글 작성에 실패하셨습니다");
                return "redirect:/alert";
            }
    }*/
/*
    @GetMapping("/board/{id}")
    public PostInfoResponseDto searchById(@PathVariable Long id) {
        List<PhotoResponseDto> photoResponseDtoList =
                photoService.findAllByPostInfo(id);

        List<String> photoPath = new ArrayList<>();

        for (PhotoResponseDto photoResponseDto : photoResponseDtoList) {
            photoPath.add(photoResponseDto.getFilePath());
        }

        return postService.searchById(id,);
    }*/

    @PostMapping("/find_post/likeAndDisLike")
    @ResponseBody
    public String likeAndDisLike(@RequestBody HashMap<String, Object> data,
                                 @CookieValue(name = "users") Optional<Cookie> cookie,
                                 RedirectAttributes redirectAttributes){
        if (cookie.isPresent()) {
            PostDto postDto = postService.findPost(Long.parseLong(data.get("id").toString()));
            Users users = userService.findById(cookie.get().getValue()).get();

            if (data.get("type").equals("like")) { // Like 일 때
                boolean anyMatch = postDto
                        .getPostLikeAndDislike()
                        .getLikedUser()
                        .stream().anyMatch(users1 -> users1.equals(users));

                String sizeOfLikeList;
                if (anyMatch) { // 이미 Like 했을 때
                    sizeOfLikeList = postService.RemoveUserListValue(postDto.getPostLikeAndDislike().getLikedUser(), users);// PostLikeAndDislike의 likedUser에서 User 제거
                } else { // Like 하지 않았을 때
                    sizeOfLikeList = postService.AddUserListValue(postDto.getPostLikeAndDislike().getLikedUser(),users);
                }

                return sizeOfLikeList;
            } else { // DisLike 일 때
                boolean anyMatch = postDto
                        .getPostLikeAndDislike()
                        .getDisLikedUser()
                        .stream().anyMatch(users1 -> users1.equals(users));
                String sizeOfDislikeList;
                if (anyMatch) { // 이미 DisLike 했을 때
                    sizeOfDislikeList = postService.RemoveUserListValue(postDto.getPostLikeAndDislike().getDisLikedUser(), users);// PostLikeAndDislike의 DislikedUser에서 User 제거
                } else { // DisLike 하지 않았을 때
                    sizeOfDislikeList = postService.AddUserListValue(postDto.getPostLikeAndDislike().getDisLikedUser(),users);
                }
                return sizeOfDislikeList;

            }
        }
        else { // 로그인이 안 되어 있을 시
            return "not_logined";
        }
    }
}

