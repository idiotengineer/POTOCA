package My_Project.integration.controller;

import My_Project.integration.entity.Dto.LoginDto;
import My_Project.integration.entity.Users;
import My_Project.integration.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Optional;

@Api(tags = {"로그인 API"})
@Controller
public class LoginController {

    private final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private
    UserService userService;
    @RequestMapping(value = "/trylogin", method = RequestMethod.POST)
    @ApiOperation(value = "로그인 API")
    public String login(LoginDto loginDto,@ApiIgnore RedirectAttributes redirectAttributes) {
        try {
            Optional<Users> users = userService.login(loginDto);
            if(users.isPresent()) {
                LOGGER.info("로그인 성공");
                redirectAttributes.addAttribute("string",(users.get().getEmail() + " 로그인 되었습니다."));
                return "redirect:/alert";
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("로그인 실패");
            redirectAttributes.addAttribute("string", "로그인 실패하였습니다");
            return "redirect:/alert";
        }
        LOGGER.warn("로그인 에러 발생");
        redirectAttributes.addAttribute("string", "로그인 실패하였습니다");
        return "redirect:/alert";
    }
}
