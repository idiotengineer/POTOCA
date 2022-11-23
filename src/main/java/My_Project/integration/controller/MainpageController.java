package My_Project.integration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainpageController {

    @GetMapping("/")
    public String mainPage() {
        return "practice";
    }

    @GetMapping("/listpage")
    public String listPage() {
        return "listpage_copy";
    }

    @GetMapping("/mypoint")
    public String myPoint() {
        return "mypoint";
    }

    @GetMapping("/votepost")
    public String votePost() {
        return "vote_post";
    }

    @GetMapping("/post")
    public String post() {
        return "post";
    }

    @GetMapping("/votelistpage")
    public String voteListPage(){
        return "votelistpage";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "loginpage";
    }


    @GetMapping("/signup")
    public String signUpPage(){
        return "signup";
    }

    @GetMapping("/forgotpassword")
    public String forgotPasswordPage(){
        return "forgot_password";
    }

    @GetMapping("/voteposting")
    public String votePostingPage() {
        return "vote_posting";
    }

    @GetMapping("/posting")
    public String postingPage() {
        return "posting";
    }
}
