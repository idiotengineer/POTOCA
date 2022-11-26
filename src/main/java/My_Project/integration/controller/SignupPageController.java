package My_Project.integration.controller;

import My_Project.integration.entity.Address;
import My_Project.integration.entity.Dto.UserInfoDto;
import My_Project.integration.entity.PointHistory;
import My_Project.integration.entity.PostInfo;
import My_Project.integration.entity.Users;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SignupPageController {

    @PostMapping("/signup_execute")
    public String signUp(UserInfoDto userInfoDto) {
        Users users = new Users(
                userInfoDto.getId();

        )
    }
}
