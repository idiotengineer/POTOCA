package My_Project.integration.controller;

import My_Project.integration.entity.Dto.UserInfoDto;
import My_Project.integration.entity.Users;
import My_Project.integration.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SignupPageController {
    @Autowired private UserService userService;
    @PostMapping("/signup_execute")
    public String signUp(UserInfoDto userInfoDto) {
        try {
            System.out.println("userInfoDto.getEmail() = " + userInfoDto.getEmail());
            System.out.println("userInfoDto.getPassword() = " + userInfoDto.getPassword());
            Users users = new Users(userInfoDto);
            userService.addUsers(users);
        } catch (Exception e){
            e.printStackTrace();
            return "redirect:/errorpage";
        }
        return "redirect:/";
    }

    @GetMapping("/errorpage")
    public String errorPageUp() {
        return "errorpage";
    }
}
