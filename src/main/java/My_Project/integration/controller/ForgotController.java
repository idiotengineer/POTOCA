package My_Project.integration.controller;

import My_Project.integration.entity.Dto.FindEmailDto;
import My_Project.integration.entity.Dto.FindPasswordDto;
import My_Project.integration.entity.Users;
import My_Project.integration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class ForgotController {

    @Autowired
    private UserService userService;


    @GetMapping("/alert")
    public String alertPage(@RequestParam("string") String s, Model model) {
        model.addAttribute("string", s);
        return "alert";
    }

    @RequestMapping(value = "/find_email",method = {RequestMethod.POST})
    public String findEmail(FindEmailDto findEmailDto, RedirectAttributes redirectAttributes) {
        try {
            Optional<Users> users = userService.findEmail(findEmailDto);
            if (users.isPresent()) {
                String email = users.get().getEmail();
                redirectAttributes.addAttribute("string",email);
                return "redirect:/alert";
            }
        } catch (Exception e) {
            redirectAttributes.addAttribute("string","없는 회원정보 입니다");
            return "redirect:/alert";
        }
        redirectAttributes.addAttribute("string","없는 회원정보 입니다");
        return "redirect:/alert";
    }

    @RequestMapping(value = "/find_email2",method = {RequestMethod.POST})
    public String findEmail2(FindPasswordDto findPasswordDto, RedirectAttributes redirectAttributes) {
        try {
            Optional<Users> users = userService.findPassword(findPasswordDto);
            if (users.isPresent()) {
                String password = users.get().getPassword();
                redirectAttributes.addAttribute("string",password);
                return "redirect:/alert";
            }
        } catch (Exception e) {
            redirectAttributes.addAttribute("string","없는 회원정보 입니다");
            return "redirect:/alert";
        }
        redirectAttributes.addAttribute("string","없는 회원정보 입니다");
        return "redirect:/alert";
    }
}
