package My_Project.integration.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Api(tags = {"페이지 이동 API"})
@Controller
public class MainpageController {
    @ApiOperation(value = "메인 페이지")
    @GetMapping("/")
    public String mainPage() {
        return "practice";
    }

    @ApiOperation(value = "리스트 페이지")
    @GetMapping("/listpage")
    public String listPage() {
        return "listpage_copy";
    }

    @ApiOperation(value = "포인트 페이지")
    @GetMapping("/mypoint")
    public String myPoint() {
        return "mypoint";
    }

    @ApiOperation(value = "투표 게시글 페이지")
    @GetMapping("/votepost")
    public String votePost() {
        return "vote_post";
    }

    @ApiOperation(value = "일반 게시글 페이지")
    @GetMapping("/post")
    public String post() {
        return "post";
    }
    @ApiOperation(value = "투표 게시글 리스팅 페이지")
    @GetMapping("/votelistpage")
    public String voteListPage(){
        return "votelistpage";
    }

    @ApiOperation(value = "로그인 페이지")
    @GetMapping("/login")
    public String loginPage(){
        return "loginpage";
    }

    @ApiOperation(value = "회원가입 페이지")
    @GetMapping("/signup")
    public String signUpPage(){
        return "signup";
    }

    @ApiOperation(value = "비밀번호/아이디 찾기 페이지")
    @GetMapping("/forgotpassword")
    public String forgotPasswordPage(){
        return "forgot_password";
    }

    @ApiOperation(value = "투표 게시글 작성 페이지")
    @GetMapping("/voteposting")
    public String votePostingPage() {
        return "vote_posting";
    }

    @ApiOperation(value = "일반 게시글 작성 페이지")
    @GetMapping("/posting")
    public String postingPage() {
        return "posting";
    }
}
