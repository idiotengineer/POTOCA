package My_Project.integration.controller;

import My_Project.integration.entity.*;
import My_Project.integration.entity.Dto.UserInfoDto;
import My_Project.integration.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SignupPageController {

    @Autowired private UserService userService;
    @PostMapping("/signup_execute")
    public String signUp(UserInfoDto userInfoDto) {
        try {
            Users users = new Users(userInfoDto);
            userService.addUsers(users);
        } catch (Exception e){
            e.printStackTrace();
            return "redirect:/errorpage";
        }
        return "redirect:/";
    }
}
