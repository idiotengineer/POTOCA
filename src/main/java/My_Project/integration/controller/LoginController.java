package My_Project.integration.controller;

import My_Project.integration.entity.Dto.LoginDto;
import My_Project.integration.entity.Users;
import My_Project.integration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class LoginController {


    @Autowired
    private
    UserService userService;
    @RequestMapping(value = "/trylogin", method = RequestMethod.POST)
    public String login(LoginDto loginDto, RedirectAttributes redirectAttributes) {
        try {
            Optional<Users> users = userService.login(loginDto);
            if(users.isPresent()) {
                redirectAttributes.addAttribute("string",(users.get().getEmail() + " 로그인 되었습니다."));
                return "redirect:/alert";
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("string", "로그인 실패하였습니다");
            return "redirect:/alert";
        }
        redirectAttributes.addAttribute("string", "로그인 실패하였습니다");
        return "redirect:/alert";
    }
}
