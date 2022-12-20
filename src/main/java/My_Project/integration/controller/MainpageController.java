package My_Project.integration.controller;

import My_Project.integration.entity.Dto.PostDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Api(tags = {"페이지 이동 API"})
@Controller
public class MainpageController {

    private final Logger LOGGER = LoggerFactory.getLogger(MainpageController.class);

    @ApiOperation(value = "메인 페이지")
    @GetMapping("/")
    public String mainPage(@CookieValue(name = "users") Optional<Cookie> cookie
            , Model model) {
        LOGGER.info("메인 페이지 접속");
        if (cookie.isPresent()) {
            model.addAttribute("users", cookie.get().getValue());
        } else {
            model.addAttribute("users",null);
        }
        return "practice";
        /*
        if (Arrays.stream(cookies).findAny().isPresent()) {
            return "practice_login";
        } else {
            return "practice";
        }*/
    }

    @ApiOperation(value = "일반 게시글 리스트 페이지 접속")
    @GetMapping("/listpage")
    public String listPage() {
        LOGGER.info("리스트페이지 접속");
        return "listpage_copy";
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
    public String post(Model model, PostDto postDto) {
        LOGGER.info("일반 게시글 페이지 접속");
        model.addAttribute(postDto);
        return "post";
    }
    @ApiOperation(value = "투표 게시글 리스팅 페이지")
    @GetMapping("/votelistpage")
    public String voteListPage(){
        LOGGER.info("투표 게시글 리스팅 페이지 접속");
        return "votelistpage";
    }

    @ApiOperation(value = "로그인 페이지")
    @GetMapping("/login")
    public String loginPage(){
        LOGGER.info("로그인 페이지 접속");
        return "loginpage";
    }

    @ApiOperation(value = "회원가입 페이지")
    @GetMapping("/signup")
    public String signUpPage(){
        LOGGER.info("회원가입 페이지 접속");
        return "signup";
    }

    @ApiOperation(value = "비밀번호/아이디 찾기 페이지")
    @GetMapping("/forgotpassword")
    public String forgotPasswordPage(){
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
}
