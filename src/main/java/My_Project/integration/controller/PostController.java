package My_Project.integration.controller;

import My_Project.integration.entity.*;
import My_Project.integration.entity.Dto.*;
import My_Project.integration.entity.ResponseDto.ModiyingPostResponseDto;
import My_Project.integration.entity.ResponseDto.PhotoResponseDto;
import My_Project.integration.exception.NotSameStringException;
import My_Project.integration.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    PhotoService photoService;

    @Autowired
    UserService userService;

    @Autowired
    PostCommentsService postCommentsService;

    @Autowired
    BigCommentsService bigCommentsService;

    @PostMapping(value = "/post_execute2", consumes = {"multipart/form-data"})
    public String create(
            @RequestPart(value = "image", required = false) List<MultipartFile> files,
            PostInfoDto postInfoDto,
            @CookieValue(name = "users") Cookie cookie,
            RedirectAttributes redirectAttributes) {
        try {
            if (cookie.getValue().isEmpty()) {
                throw new Exception("로그인 하지 않았습니다");
            }

            PostDto postDto = postService.create(postInfoDto, files, cookie);
            List<PhotoResponseDto> photoResponseDtoList = photoService.findAllByPostInfo(postDto.getPostNumber());
            List<String> photoPath = new ArrayList<>();

            for (PhotoResponseDto photoResponseDto : photoResponseDtoList) {
                photoPath.add(photoResponseDto.getFilePath());
            }

            redirectAttributes.addAttribute("id",postDto.getPostNumber());
            redirectAttributes.addFlashAttribute("string","게시글을 성공적으로 작성했습니다.");
            return "redirect:/find_post?id={id}";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("string", "게시글 작성에 실패하셨습니다");
            return "redirect:/alert?string={string}";
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

    @PostMapping("/find_post/likeAndDisLike")
    @ResponseBody
    public String likeAndDisLike(@RequestBody HashMap<String, Object> data,
                                 @CookieValue(name = "users") Optional<Cookie> cookie,
                                 RedirectAttributes redirectAttributes) {
        if (cookie.isPresent()) {
            Long id = Long.parseLong(data.get("id").toString());
//            PostLikeAndDislike postLikeAndDislike = postService.findPostlidiByPostNumber(id);
            PostInfo post = postService.findPost(id);
            PostLikeAndDislike postLikeAndDislike = post.getPostLikeAndDislike();

            Users users = userService.findById(cookie.get().getValue()).get();
            if (data.get("type").equals("like")) { // Like 일 때
                boolean anyMatch = postService
                        .findLikedByUsersAndPostLidi(postLikeAndDislike, users)
                        .isPresent();

                String sizeOfDislikeList;

                if (anyMatch) { // 이미 Like 했을 때
                    sizeOfDislikeList = postService.removeUsersSet(postLikeAndDislike, users, "like");
                    postService.setLikeCount(id,Long.parseLong(sizeOfDislikeList));
                } else { // Like 하지 않았을 때
                    sizeOfDislikeList = postService.addUsersSet(postLikeAndDislike, users, "like");
                    postService.setLikeCount(id,Long.parseLong(sizeOfDislikeList));
                }

                return sizeOfDislikeList;
            } else { // DisLike 일 때
                boolean anyMatch = postService
                        .findDisLikedByUsersAndPostLidi(postLikeAndDislike, users)
                        .isPresent();

                String sizeOfDislikeList;
                if (anyMatch) { // 이미 DisLike 했을 때
                    sizeOfDislikeList = postService.removeUsersSet(postLikeAndDislike, users, "dislike");
                } else { // DisLike 하지 않았을 때
                    sizeOfDislikeList = postService.addUsersSet(postLikeAndDislike, users, "dislike");
                }

                return sizeOfDislikeList;
            }
        } else { // 로그인이 안 되어 있을 시
            return "not_logined";
        }
    }

    // 댓글 좋아요 기능 API
    @PostMapping("/find_post/commentLikeAndDisLike")
    @ResponseBody
    public String commentLikeAndDisLike(@RequestBody HashMap<String, Object> data,
                                        @CookieValue(name = "users") Optional<Cookie> cookie,
                                        RedirectAttributes redirectAttributes) {
        if (cookie.isPresent()) {
            try {
                Long postNumber = Long.parseLong(data.get("id").toString());
                Long commentNumber = Long.parseLong(data.get("commentNumber").toString());

                PostLikeAndDislike postLikeAndDislike = postCommentsService
                        .findPostCommentsById(commentNumber)
                        .get()
                        .getPostLikeAndDislike();

                Users users = userService.findById(cookie.get().getValue()).get();

                if (data.get("type").equals("like")) { // Like 일 때
                    boolean anyMatch = postLikeAndDislike
                            .getLiked().stream()
                            .anyMatch(
                                    liked -> liked.getUsers().equals(users)
                            );

                    String sizeOfDislikeList;
                    if (anyMatch) { // 이미 Like 했을 때
                        sizeOfDislikeList = postService.removeUsersSet(postLikeAndDislike, users, "like");
                    } else { // Like 하지 않았을 때
                        sizeOfDislikeList = postService.addUsersSet(postLikeAndDislike, users, "like");
                    }

                    return sizeOfDislikeList;
                } else { // DisLike 일 때
                    boolean anyMatch = postLikeAndDislike
                            .getDisLiked().stream()
                            .anyMatch(
                                    disLiked -> disLiked.getUsers().equals(users)
                            );
                    String sizeOfDislikeList;
                    if (anyMatch) { // 이미 DisLike 했을 때
                        sizeOfDislikeList = postService.removeUsersSet(postLikeAndDislike, users, "dislike");
                    } else { // DisLike 하지 않았을 때
                        sizeOfDislikeList = postService.addUsersSet(postLikeAndDislike, users, "dislike");
                    }

                    return sizeOfDislikeList;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.out.println("JSON 데이터 형식 변환 오류");
                return "0";
            } catch (NoSuchElementException e) {
                e.printStackTrace();
                System.out.println("PostLikeAndDisLike 조회 실패 or 로그인된 정보 조회 (Users) 실패");
                return "0";
            } catch (Exception e) {
                e.printStackTrace();
                return "0";
            }
        } else { // 로그인이 안 되어 있을 시
            return "not_logined";
        }
    }



    @RequestMapping(value = "/find_post/deletePost", method = {RequestMethod.POST})
    public String deletePost(@CookieValue("users") Optional<Cookie> cookie,
                             @RequestBody HashMap<String, Object> data,
                             RedirectAttributes redirectAttributes) {
        try {
            Long id = Long.parseLong(data.get("id").toString());
            postService.deletePost(id);
            redirectAttributes.addAttribute("string", "게시글이 삭제 되었습니다");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("string", "에러 발생");
        }
        return "redirect:/find_post/deletePost/alert";
    }

    @GetMapping("/find_post/deletePost/alert")
    public String deleteResultAlert(@RequestParam("string") String s, Model model) {
        model.addAttribute("string", s);
        return "secondAlert";
    }

    @RequestMapping(value = "/moveToModifyingPage", method = {RequestMethod.POST})
    public ResponseEntity<ModiyingPostResponseDto> modifyingPost(
            @RequestBody HashMap<String, Object> data,
            @CookieValue("users") Optional<Cookie> cookie
    ) {
        try {
            Long postNumber = Long.parseLong(data.get("id").toString());
            ModiyingPostResponseDto ResponseDto = ModiyingPostResponseDto.builder()
                    .value(postNumber.toString())
                    .build();

            return new ResponseEntity<>(ResponseDto, HttpStatus.OK);

        } catch (Exception e) {
            ModiyingPostResponseDto ResponseDto = ModiyingPostResponseDto.builder()
                    .value("error")
                    .build();

            return new ResponseEntity<>(ResponseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/modifyingPage{id}", method = {RequestMethod.GET})
    public String modifyingPage(
            @RequestParam(value = "id") Long postNumber,
            @CookieValue("users") Cookie cookie,
            Model model) {
        PostInfo post = postService.findPost(postNumber);
        PostDto postDto = new PostDto(post);

        if (cookie.getValue().equals(postDto.getUsers().getEmail())) {
            model.addAttribute(postDto);
            System.out.println("---------------------------------------------------------------------------------------------------------------");
            return "modifying";
        } else {
            model.addAttribute("string", "Not logined");
            System.out.println("---------------------------------------------------------------------------------------------------------------");
            return "alert";
        }
    }

    @PostMapping(value = "/modifyingRequest", consumes = "multipart/form-data")
    public String modifyingRequest(
            @RequestPart(value = "image", required = false) List<MultipartFile> files,
            ModifyDto modifyDto,
            @CookieValue(name = "users") Cookie cookie) {

        try {
            PostInfo post = postService.findPost(modifyDto.getPostNumber());

            if (!modifyDto.getUsers().equals(cookie.getValue())) {
                throw new NotSameStringException();
            }

            modifyDto.setFiles(files);
            postService.modifyingPost(post, modifyDto);

            return "redirect:/find_post?id=" + modifyDto.getPostNumber();
        } catch (NotSameStringException notSameStringException) {
            notSameStringException.printStackTrace();
            return "redirect:/alert?string=" + notSameStringException.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/alert?string='Failed Modifying'";
        }
    }

    @PostMapping("/find_post/addBigComments")
    @ResponseBody
    public String addBigComments(
            @RequestBody addBigCommentsDto addBigCommentsDto,
            @CookieValue("users") Optional<Cookie> cookie) {
        if (!cookie.isPresent()) {
            return "not_logined";
        }

        postService.addBigComments(addBigCommentsDto, cookie.get().getValue());
        return "success";
    }

    @PostMapping("/find_post/bigCommentLikeAndDisLike")
    @ResponseBody
    public String bigCommentLikeAndDisLike(@RequestBody HashMap<String, Object> data,
                                        @CookieValue(name = "users") Optional<Cookie> cookie,
                                        RedirectAttributes redirectAttributes) {
        if (cookie.isPresent()) {
            try {
                Long postNumber = Long.parseLong(data.get("id").toString());
                Long bigCommentNumber = Long.parseLong(data.get("bigCommentNumber").toString());

//                PostLikeAndDislike postLikeAndDislike = postCommentsService
//                        .findPostCommentsById(commentNumber)
//                        .get()
//                        .getPostLikeAndDislike();

                PostLikeAndDislike postLikeAndDislike = bigCommentsService.findBigCommentsById(bigCommentNumber)
                        .get().getPostLikeAndDislike();

                Users users = userService.findById(cookie.get().getValue()).get();

                if (data.get("type").equals("like")) { // Like 일 때
                    boolean anyMatch = postLikeAndDislike
                            .getLiked().stream()
                            .anyMatch(liked -> liked.getUsers().equals(users)
                            );

                    String sizeOfDislikeList;
                    if (anyMatch) { // 이미 Like 했을 때
                        sizeOfDislikeList = postService.removeUsersSet(postLikeAndDislike, users, "like");
                    } else { // Like 하지 않았을 때
                        sizeOfDislikeList = postService.addUsersSet(postLikeAndDislike, users, "like");
                    }

                    return sizeOfDislikeList;
                } else { // DisLike 일 때
                    boolean anyMatch = postLikeAndDislike
                            .getDisLiked().stream()
                            .anyMatch(disLiked -> disLiked.getUsers().equals(users)
                            );
                    String sizeOfDislikeList;
                    if (anyMatch) { // 이미 DisLike 했을 때
                        sizeOfDislikeList = postService.removeUsersSet(postLikeAndDislike, users, "dislike");
                    } else { // DisLike 하지 않았을 때
                        sizeOfDislikeList = postService.addUsersSet(postLikeAndDislike, users, "dislike");
                    }

                    return sizeOfDislikeList;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.out.println("JSON 데이터 형식 변환 오류");
                return "0";
            } catch (NoSuchElementException e) {
                e.printStackTrace();
                System.out.println("PostLikeAndDisLike 조회 실패 or 로그인된 정보 조회 (Users) 실패");
                return "0";
            } catch (Exception e) {
                e.printStackTrace();
                return "0";
            }
        } else { // 로그인이 안 되어 있을 시
            return "not_logined";
        }
    }


    public boolean likedButtonCheckedWhether(Set<Liked> set,Optional<Users> users1) throws Exception {
        Users users = users1.get();

        if (set == null) return false;
        else if(set.isEmpty()) return false;
        else if(!set.stream().anyMatch(liked -> liked.getUsers().equals(users))) return false;
        else if (set.stream().anyMatch(liked -> liked.getUsers().equals(users))) return true;
        else throw new Exception("PostController.likedButtonCheckedWhether 메서드 에러발생");
    }

    public boolean disLikedButtonCheckedWhether(Set<DisLiked> set,Optional<Users> users1) throws Exception {
        Users users = users1.get();

        if (set == null) return false;
        else if(set.isEmpty()) return false;
        else if(!set.stream().anyMatch(disLiked -> disLiked.getUsers().equals(users))) return false;
        else if (set.stream().anyMatch(disLiked -> disLiked.getUsers().equals(users))) return true;
        else throw new Exception("PostController.disLikedButtonCheckedWhether 메서드 에러발생");
    }
}

