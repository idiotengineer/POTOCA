package My_Project.integration.controller;

import My_Project.integration.entity.Dto.LoginDto;
import My_Project.integration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginController {


    @Autowired
    private
    UserService userService;

    @GetMapping("/loginSuccess")
    public String loginSuccess() {
        return "login_success";
    }

    @GetMapping("/loginFailed")
    public String loginFailed()  {
        return "login_failed";
    }

    @GetMapping("/trylogin")
    public String login(LoginDto loginDto) {
        try {
            RedirectView redirectView = new RedirectView();
            if(userService.login(loginDto).isPresent()) {
                return "redirect:/loginSuccess";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/loginFailed";
        }
        return "redirect:/loginFailed";
    }
}
