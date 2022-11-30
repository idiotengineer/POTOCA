package My_Project.integration.controller;

import My_Project.integration.entity.Dto.LoginDto;
import My_Project.integration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LoginController {


    @Autowired private
    UserService userService;

    @GetMapping("/trylogin")
    public String login(Model model,LoginDto loginDto) {
        if (userService.login(loginDto)) {
            model.addAttribute("return_value",true);
        } else {
            model.addAttribute("return_value",false);
        }
        return "loginpage";
    }
}
