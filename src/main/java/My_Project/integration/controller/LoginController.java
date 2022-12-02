package My_Project.integration.controller;

import My_Project.integration.entity.Dto.LoginDto;
import My_Project.integration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.NoResultException;

@Controller
public class LoginController {


    @Autowired
    private
    UserService userService;

    @GetMapping("/trylogin")
    public String login(LoginDto loginDto) {
        try {
            if (userService.login(loginDto)) {
                return "redirect:/login_success";
            }
        } catch (NoResultException e){
            return "redirect:/login_failed";
        } catch (EmptyResultDataAccessException e){
            return "redirect:/login_failed";
        }
        return "redirect:/login_failed";
    }

    @GetMapping("/login_success")
    public String loginSuccess() {
        return "login_success";
    }

    @GetMapping("/login_failed")
    public String loginFailed() {
        return "login_failed";
    }
}
