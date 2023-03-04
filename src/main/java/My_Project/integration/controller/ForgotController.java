package My_Project.integration.controller;

import My_Project.integration.entity.Dto.FindEmailDto;
import My_Project.integration.entity.Dto.FindPasswordDto;
import My_Project.integration.entity.Users;
import My_Project.integration.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Optional;

@Api(tags = {"회원정보 찾기 API"})
@Controller
public class ForgotController {

    @Autowired
    private UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(ForgotController.class);

    @GetMapping("/alert")
    @ApiOperation(value = "결과 알림 페이지")
    public String alertPage(@RequestParam("string") String s,@ApiIgnore Model model) {
        model.addAttribute("string", s);
        return "alert";
    }

    @ApiOperation(value = "이메일 찾기 API")
    @RequestMapping(value = "/find_email",method = {RequestMethod.POST})
    public String findEmail(FindEmailDto findEmailDto,@ApiIgnore RedirectAttributes redirectAttributes) {
        try {
            Optional<Users> users = userService.findEmail(findEmailDto);
            if (users.isPresent()) {
                LOGGER.info("이메일 찾기 성공");
                String email = users.get().getEmail();
                redirectAttributes.addAttribute("string",email);
                return "redirect:/alert";
            }
        } catch (Exception e) {
            LOGGER.info("이메일 찾기 실패 ");
            redirectAttributes.addAttribute("string","없는 회원정보 입니다");
            return "redirect:/alert";
        }
        LOGGER.info("이메일 찾기 실패");
        redirectAttributes.addAttribute("string","없는 회원정보 입니다");
        return "redirect:/alert";
    }

    @ApiOperation("패스워드 찾기 API")
    @RequestMapping(value = "/find_email2",method = {RequestMethod.POST})
    public String findEmail2(FindPasswordDto findPasswordDto, @ApiIgnore RedirectAttributes redirectAttributes) {
        try {
            Optional<Users> users = userService.findPassword(findPasswordDto);
            if (users.isPresent()) {
                LOGGER.info("패스워드 찾기 성공");
                String password = users.get().getPassword();
                redirectAttributes.addAttribute("string",password);
                return "redirect:/alert";
            }
        } catch (Exception e) {
            LOGGER.info("패스워드 찾기 실패 에러 발생");
            redirectAttributes.addAttribute("string","없는 회원정보 입니다");
            return "redirect:/alert";
        }
        LOGGER.info("패스워드 찾기 실패");
        redirectAttributes.addAttribute("string","없는 회원정보 입니다");
        return "redirect:/alert";
    }
}

