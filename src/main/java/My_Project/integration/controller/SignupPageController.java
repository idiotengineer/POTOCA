package My_Project.integration.controller;

import My_Project.integration.entity.Dto.UserInfoDto;
import My_Project.integration.entity.Users;
import My_Project.integration.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Api(tags = {"회원가입 API"})
public class SignupPageController {
    @Autowired private UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(SignupPageController.class);

    @PostMapping("/signup_execute")
    @ApiOperation(value = "회원가입 API")
    public String signUp(UserInfoDto userInfoDto) {
        try {
            System.out.println("userInfoDto.getEmail() = " + userInfoDto.getEmail());
            System.out.println("userInfoDto.getPassword() = " + userInfoDto.getPassword());
            Users users = new Users(userInfoDto);
            userService.addUsers(users);
        } catch (Exception e){
            LOGGER.error("회원가입 실패");
            e.printStackTrace();
            return "redirect:/errorpage";
        }
        LOGGER.error("회원가입 성공");
        return "redirect:/";
    }

    @GetMapping("/errorpage")
    @ApiOperation(value = "가입 실패 시 에러페이지")
    public String errorPageUp() {
        return "errorpage";
    }
}
