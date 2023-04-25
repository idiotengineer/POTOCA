package My_Project.integration.controller;

import My_Project.integration.entity.Dto.PostDto;
import My_Project.integration.entity.PostInfo;
import My_Project.integration.entity.ResponseDto.PostCommentsResponseDto;
import My_Project.integration.entity.Users;
import My_Project.integration.service.PostService;
import My_Project.integration.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Api(tags = {"페이지 이동 API"})
@Controller
public class MainpageController {

    private final Logger LOGGER = LoggerFactory.getLogger(MainpageController.class);

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "메인 페이지")
    @GetMapping("/")
    public String mainPage(@CookieValue(name = "users") Optional<Cookie> cookie
            , Model model) {
        LOGGER.info("메인 페이지 접속");
        List<PostInfo> best4Post = postService.findBest4Post();
        List<PostInfo> todays10Post = postService.findTodays10Post();
        if (cookie.isPresent()) {
            model.addAttribute("users", cookie.get().getValue());
        } else {
            model.addAttribute("users", null);
        }
        model.addAttribute("todays10Post",todays10Post);
        model.addAttribute("best4Post",best4Post);

        return "practice";
        /*
        if (Arrays.stream(cookies).findAny().isPresent()) {
            return "practice_login";
        } else {
            return "practice";
        }*/
    }


    @ApiOperation(value = "포인트 페이지")
    @GetMapping("/mypoint")
    public String myPoint() {
        LOGGER.info("포인트 페이지 접속");
        return "mypoint";
    }

    @ApiOperation(value = "투표 게시글 페이지")
    @GetMapping("/votepost")
    public String votePost() {
        LOGGER.info("투표 게시글 페이지 접속");
        return "vote_post";
    }

    @ApiOperation(value = "일반 게시글 페이지")
    @GetMapping("/post")
    public String post(Model model,
                       @RequestParam("photoPath") List<String> photoPath,
                       @RequestParam("time") LocalDateTime time,
                       @RequestParam("postDtoEmail") String postDtoEmail,
                       @RequestParam("postDtoGetPostContent") String postDtoGetPostContent,
                       @ModelAttribute PostDto postDto) {
        LOGGER.info("일반 게시글 페이지 접속");
        model.addAttribute("postDtoEmail", postDtoEmail);
        model.addAttribute("postDtoGetPostContent", postDtoGetPostContent);
        model.addAttribute("time", getTimeDiffAndReturnElapsedTime(time, LocalDateTime.now()));
        model.addAttribute("photoPath", photoPath);
        model.addAttribute("postDto", postDto);
        return "post";
    }

    @RequestMapping(value="/find_post",method = {RequestMethod.GET,RequestMethod.POST})
    public String findPost(@RequestParam("id") Long id, Model model, @CookieValue("users") Optional<Cookie> cookie,@ModelAttribute("string") String s) {
        try {
            PageRequest of = PageRequest.of(0, 10);

//            PostInfo postInfo = postService.findPostV2(id).get();
            PostInfo postInfo = postService.findPostV3(id).get();
//            PostInfo postInfo = postService.findPostByIdWithSpringDataJpa(id).get();

//            Slice<PostCommentsResponseDto> commentsV2 = postService.findCommentsV2(postInfo, of);
            PostDto post = new PostDto(postInfo);
            boolean logined = true;

            if (cookie.isPresent()) { // 로그인 되어 있을 시
                Optional<Users> usersOptional = userService.findById(cookie.get().getValue());
                model.addAttribute("checked", post.checkLikeAndDisLike(usersOptional));
                model.addAttribute("cookie",usersOptional);
            } else {
                logined = false;
            }

            model.addAttribute("logined",logined);
            model.addAttribute("post", post);
            model.addAttribute("time", LocalDateTime.now());

            if (StringUtils.hasText(s)) {
                model.addAttribute("string",s);
            }

            return "copy_post";
        } catch (NoSuchElementException e) {
            model.addAttribute("string", e.toString());
            return "alert";
        }
    }

    public String getTimeDiffAndReturnElapsedTime(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        long year = duration.getSeconds() / 31556926;
        long month = duration.getSeconds() / 2629800;
        long day = duration.getSeconds() / 86400;
        long hour = duration.getSeconds() / 3600;
        long minute = duration.getSeconds() / 60;
        if (year > 0) { //
            return year + "년 전";
        } else if (month > 0) {
            return month + "개월 전";
        } else if (day > 0) {
            return day + "일 전";
        } else if (hour > 0) {
            return hour + "시간 전";
        } else if (minute > 0) {
            return minute + "분 전";
        } else {
            return duration.getSeconds() + "초 전";
        }
    }

//    public String commentsLidiCheckedSearch(String LoginedEmail, Long commentsNumber) {
//        postService.
//    }

    @ApiOperation(value = "투표 게시글 리스팅 페이지")
    @GetMapping("/votelistpage")
    public String voteListPage() {
        LOGGER.info("투표 게시글 리스팅 페이지 접속");
        return "votelistpage";
    }

    @ApiOperation(value = "로그인 페이지")
    @GetMapping("/login")
    public String loginPage() {
        LOGGER.info("로그인 페이지 접속");
        return "loginpage";
    }

    @ApiOperation(value = "회원가입 페이지")
    @GetMapping("/signup")
    public String signUpPage() {
        LOGGER.info("회원가입 페이지 접속");
        return "signup";
    }

    @ApiOperation(value = "비밀번호/아이디 찾기 페이지")
    @GetMapping("/forgotpassword")
    public String forgotPasswordPage() {
        LOGGER.info("비밀번호/아이디 찾기 페이지 접속");
        return "forgot_password";
    }

    @ApiOperation(value = "투표 게시글 작성 페이지")
    @GetMapping("/voteposting")
    public String votePostingPage() {
        LOGGER.info("투표 게시글 작성 페이지 접속");
        return "vote_posting";
    }

    @ApiOperation(value = "일반 게시글 작성 페이지")
    @GetMapping("/posting")
    public String postingPage() {
        LOGGER.info("일반 게시글 작성 페이지 접속");
        return "posting";
    }

    @GetMapping("/test")
    public String sdf() {
        return "test";
    }

}
