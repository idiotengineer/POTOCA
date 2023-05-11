package My_Project.integration.controller;

import My_Project.integration.entity.Dto.ExchangeDto;
import My_Project.integration.entity.Enum.PlusOrMinus;
import My_Project.integration.entity.Enum.UsingType;
import My_Project.integration.entity.QPointHistory;
import My_Project.integration.entity.Users;
import My_Project.integration.repository.UsersRepository;
import My_Project.integration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import java.util.Optional;

@Controller
public class PointController {

    @Autowired
    UserService userService;

    @PostMapping("/LOL_Exchange")
    public String LOLExchange(ExchangeDto exchangeDto, @CookieValue("users") Optional<Cookie> cookie, RedirectAttributes redirectAttributes) {
        if (cookie.isPresent()) {
            Users users = userService.findById(cookie.get().getValue()).get();

            if (exchangeDto.getPoint() <= users.getPoint()) {
                userService.insertUsersPointHistory(users,exchangeDto, UsingType.CHARGE,PlusOrMinus.MINUS,"리그오브레전드에 캐쉬로 환전");
                String s = "리그오브레전드 게임으로 " + exchangeDto.getPoint() + " 포인트 환전 되었습니다";
                redirectAttributes.addFlashAttribute("string", s);
            } else {
                redirectAttributes.addFlashAttribute("string", "포인트가 부족합니다");
            }
        } else {
            redirectAttributes.addFlashAttribute("string","로그인 되지 않았습니다");
        }

        return "/alert";
    }
}
