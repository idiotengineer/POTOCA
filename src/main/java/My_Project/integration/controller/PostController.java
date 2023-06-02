package My_Project.integration.controller;

import My_Project.integration.entity.*;
import My_Project.integration.entity.Dto.*;
import My_Project.integration.entity.ResponseDto.DisLikedResponseDto;
import My_Project.integration.entity.ResponseDto.LikedResponseDto;
import My_Project.integration.entity.ResponseDto.ModiyingPostResponseDto;
import My_Project.integration.entity.ResponseDto.PhotoResponseDto;
import My_Project.integration.exception.NotSameStringException;
import My_Project.integration.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Api(tags = {"게시글 API"})
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

    @Autowired
    ReportService reportService;

    private final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

    @ApiOperation(value = "게시글 작성 API",
    notes = "게시글을 작성합니다. multipart/form-data로 파일도 전송이 가능합니다 (로그인 확인 시 활용되는 cookie는 숨기겠습니다.)")
    @PostMapping(value = "/post_execute2", consumes = {"multipart/form-data"})
    public String create(
            @RequestPart(value = "image", required = false) List<MultipartFile> files,
            PostInfoDto postInfoDto,
            @ApiIgnore @CookieValue(name = "users") Cookie cookie,
            @ApiIgnore RedirectAttributes redirectAttributes) {
        try {
            if (cookie.getValue().isEmpty()) {
                throw new Exception("로그인 하지 않았습니다");
            }

            if (!Optional.ofNullable(postInfoDto.getPoint()).isPresent() || postInfoDto.getPoint().equals("")) {
                postInfoDto.setPoint(0L);
            }

            if (!Optional.ofNullable (postInfoDto.getClosingTime()).isPresent() || postInfoDto.getClosingTime().equals("") || postInfoDto.getClosingTime().equals(0L) ) {
                postInfoDto.setClosingTime(24L);
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
    @ApiOperation(
            value = "댓글 작성 API",
            notes = "어떠한 게시글에 댓글을 작성하는 API입니다. (로그인 확인 시 활용되는 cookie는 숨기겠습니다.)"
    )
    public String registerComments(
            @RequestBody CommentDto commentDto,
            @ApiIgnore @CookieValue(name = "users") Optional<Cookie> cookie,
            @ApiIgnore RedirectAttributes attr) {
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
    @ApiOperation(value = "게시글 좋아요/싫어요 API",
    notes = "게시글의 좋아요/싫어요를 처리하는 API입니다. 페이지를 리턴하지 않고,좋아요 혹은 싫어요 수를 리턴합니다. (로그인 확인 시 사용되는 cookie는 숨기겠습니다)")
    @ResponseBody
    public String likeAndDisLike(@RequestBody HashMap<String, Object> data,
                                 @ApiIgnore @CookieValue(name = "users") Optional<Cookie> cookie,
                                 @ApiIgnore RedirectAttributes redirectAttributes) {
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
    @ApiOperation(value = "댓글 좋아요/싫어요 API",
            notes = "댓글의 좋아요/싫어요를 처리하는 API입니다. 페이지를 리턴하지 않고,좋아요 혹은 싫어요 수를 리턴합니다. (로그인 확인 시 사용되는 cookie는 숨기겠습니다)")
    @ResponseBody
    public String commentLikeAndDisLike(@RequestBody HashMap<String, Object> data,
                                        @ApiIgnore @CookieValue(name = "users") Optional<Cookie> cookie,
                                        @ApiIgnore RedirectAttributes redirectAttributes) {
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
                        PostInfo postForBestPostComments = postService.findPostForBestPostComments(postNumber);
                        postService.updatePostInfoForBestPostCommentsList(postForBestPostComments);
                    } else { // Like 하지 않았을 때
                        sizeOfDislikeList = postService.addUsersSet(postLikeAndDislike, users, "like");
                        PostInfo postForBestPostComments = postService.findPostForBestPostComments(postNumber);
                        postService.updatePostInfoForBestPostCommentsList(postForBestPostComments);
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
    @ApiOperation(value = "게시글 삭제 API",
            notes = "게시글을 삭제하는 API입니다. data 파라미터에 key: id, value: 삭제하길 원하는 게시글의 번호를 입력하면 됩니다. (로그인 확인 시 사용되는 cookie는 숨기겠습니다.)")
    public String deletePost(@ApiIgnore @CookieValue("users") Optional<Cookie> cookie,
                             @RequestBody HashMap<String, Object> data,
                             @ApiIgnore RedirectAttributes redirectAttributes) {
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
    @ApiOperation(value = "게시글을 삭제 알림페이지")
    public String deleteResultAlert(@RequestParam("string") String s, Model model) {
        model.addAttribute("string", s);
        return "secondAlert";
    }

    @RequestMapping(value = "/moveToModifyingPage", method = {RequestMethod.POST})
    @ApiOperation(value = "게시글 수정 페이지 이동 전에 확인 메서드",
    notes = "게시글 수정 페이지를 이동 전, 서버에 건내는 데이터 값이 유효하면 OK, 유효하지 않으면 BAD_REQUEST 처리를 하는데 목적을 둔 API 입니다.(로그인 확인 시 사용되는 cookie는 숨기겠습니다.)")
    public ResponseEntity<ModiyingPostResponseDto> modifyingPost(
            @RequestBody HashMap<String, Object> data,
           @ApiIgnore @CookieValue("users") Optional<Cookie> cookie
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
    @ApiOperation(value = "게시글 수정 페이지 이동 API",
            notes = "게시글 수정 페이지로 이동하는 API입니다. 파라미터 postNUmber로 수정할 페이지의 ID 값을 작성하면 됩니다. (로그인 확인 시 사용되는 cookie는 숨기겠습니다.)")
    public String modifyingPage(
            @RequestParam(value = "id") Long postNumber,
            @ApiIgnore @CookieValue("users") Cookie cookie,
            @ApiIgnore Model model) {
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
    @ApiOperation(
            value = "게시글 수정 요청 API",
            notes = "게시글 수정 요청을 보내는 API입니다. ModifyDto에서 데이터를 받아옵니다. (로그인 확인 시 사용되는 cookie는 숨기겠습니다.)"
    )
    public String modifyingRequest(
            @ApiIgnore @RequestPart(value = "image", required = false) List<MultipartFile> files,
            ModifyDto modifyDto,
            @ApiIgnore @CookieValue(name = "users") Cookie cookie) {

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
    @ApiOperation(value = "대댓글 작성 API",
    notes = "게시글의 대댓글을 등록하는 API입니다. addBigComments의 게시글번호, 댓글번호, 댓글내용을 입력하여 사용가능합니다. (로그인 확인 시 사용되는 cookie는 숨기겠습니다.)")
    public String addBigComments(
            @RequestBody addBigCommentsDto addBigCommentsDto,
            @ApiIgnore @CookieValue("users") Optional<Cookie> cookie) {
        if (!cookie.isPresent()) {
            return "not_logined";
        }

        postService.addBigComments(addBigCommentsDto, cookie.get().getValue());
        return "success";
    }

    @PostMapping("/find_post/bigCommentLikeAndDisLike")
    @ResponseBody
    @ApiOperation(value = "대댓글 좋아요 API",
    notes = "대댓글의 좋아요를 처리하는 API입니다. data의 'type'이 'like'일 땐, 좋아요 처리, 'dislike'일 땐 싫어요 처리를 합니다.[key-value 형식] (로그인 확인 시 사용되는 cookie는 숨기겠습니다.)")
    public String bigCommentLikeAndDisLike(@RequestBody HashMap<String, Object> data,
                                        @ApiIgnore @CookieValue(name = "users") Optional<Cookie> cookie,
                                        @ApiIgnore RedirectAttributes redirectAttributes) {
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

    //public boolean likedButtonCheckedWhether(Set<Liked> set,Optional<Users> users1) throws Exception {
    public boolean likedButtonCheckedWhether(Set<LikedResponseDto> set, Optional<Users> users1) throws Exception {
        Users users = users1.get();

        if (set == null) return false;
        else if(set.isEmpty()) return false;
        else if(!set.stream().anyMatch(liked -> liked.getUsers().getEmail().equals(users.getEmail()))) return false;
        else if (set.stream().anyMatch(liked -> liked.getUsers().getEmail().equals(users.getEmail()))) return true;
        else throw new Exception("PostController.likedButtonCheckedWhether 메서드 에러발생");
    }

//    public boolean disLikedButtonCheckedWhether(Set<DisLiked> set,Optional<Users> users1) throws Exception {
    public boolean disLikedButtonCheckedWhether(Set<DisLikedResponseDto> set, Optional<Users> users1) throws Exception {
        Users users = users1.get();

        if (set == null) return false;
        else if(set.isEmpty()) return false;
        else if(!set.stream().anyMatch(disLiked -> disLiked.getUsers().getEmail().equals(users.getEmail()))) return false;
        else if (set.stream().anyMatch(disLiked -> disLiked.getUsers().getEmail().equals(users.getEmail()))) return true;
        else throw new Exception("PostController.disLikedButtonCheckedWhether 메서드 에러발생");
    }

    @RequestMapping(value = "/reportPost", method = RequestMethod.POST)
    @ApiOperation(value = "신고 API",
    notes = "[key-value] 형식으로 'dtype'이 'POST'일 땐 게시글 신고, 'COMMENT'일 땐 댓글 신고, 'BIGCOMMENT'일땐 대댓글 신고를 합니다. 'map'에 게시글 or 댓글 or 대댓글 번호를 입력하면 됩니다. (로그인 확인 시 사용되는 cookie는 숨기겠습니다.)")
    public ResponseEntity<String> reportMethod(
            @RequestBody HashMap<String, Object> data,
            @CookieValue("users") Optional<Cookie> cookie) {
        if (cookie.isPresent()) {
            Report newReport = reportService.createNewReport(data,cookie);
            return new ResponseEntity<>("신고가 완료 되었습니다.",HttpStatus.OK);
        }
        return new ResponseEntity<>("로그인 안됨",HttpStatus.BAD_REQUEST);
    }
}

