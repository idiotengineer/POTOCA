package My_Project.integration.controller;

import My_Project.integration.entity.Dto.FindEmailDto;
import My_Project.integration.entity.Users;
import My_Project.integration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class ForgotController {

    @Autowired
    private UserService userService;

    @GetMapping("/find_email")
    public String findEmail(FindEmailDto findEmailDto,RedirectAttributes redirectAttributes) {
            Optional<Users> users = userService.findEmail(findEmailDto);
            if (users.isPresent()) {
                String email = users.get().getEmail();
                redirectAttributes.addAttribute("value", email);
                return "/alert";
            }

        redirectAttributes.addAttribute("value","없는 회원 정보 입니다");
        return "/alert";
    }

    @GetMapping("/alert")
    public String alertPage(@RequestParam("value") String s, Model model) {
        model.addAttribute("string", s);
        return "alert";
    }
}
