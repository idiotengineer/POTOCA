package My_Project.integration.controller;

import My_Project.integration.entity.Dto.ExchangeDto;
import My_Project.integration.entity.Enum.PlusOrMinus;
import My_Project.integration.entity.Enum.UsingType;
import My_Project.integration.entity.QPointHistory;
import My_Project.integration.entity.Users;
import My_Project.integration.repository.UsersRepository;
import My_Project.integration.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import java.util.Optional;

@Controller
@Api(tags = "포인트 API")
public class PointController {

    @Autowired
    UserService userService;

    @PostMapping("/LOL_Exchange")
    @ApiOperation(value = "MyPoint -> League Of Legend로 환전 API",
    notes = "MyPoint를 League Of Legend 게임으로 환전하는 API입니다. ExchangeDto를 통해서 데이터를 받아옵니다. (로그인 확인 시 사용되는 cookie는 숨기겠습니다.)")
    public String LOLExchange(ExchangeDto exchangeDto, @ApiIgnore @CookieValue("users") Optional<Cookie> cookie, @ApiIgnore RedirectAttributes redirectAttributes) {
        if (cookie.isPresent()) {
            Users users = userService.findById(cookie.get().getValue()).get();

            if (StringUtils.isEmpty(exchangeDto.getEmail())) {
                redirectAttributes.addAttribute("string", "이메일을 입력하세요");
                return "redirect:/alert";
            }

            if (StringUtils.isEmpty(exchangeDto.getPassword())) {
                redirectAttributes.addAttribute("string", "비밀번호를 입력하세요");
                return "redirect:/alert";
            }

            if (exchangeDto.getPoint().isEmpty()) {
                redirectAttributes.addAttribute("string", "포인트를 입력하세요");
                return "redirect:/alert";
            }

            if (exchangeDto.getPoint().get() <= users.getPoint()) {
                userService.insertUsersPointHistory(users, exchangeDto, UsingType.CHARGE, PlusOrMinus.MINUS, "리그오브레전드에 캐쉬로 환전");
                String s = "리그오브레전드 게임으로 " + exchangeDto.getPoint().get() + " 포인트 환전 되었습니다";
                redirectAttributes.addAttribute("string", s);
            } else {
                redirectAttributes.addAttribute("string", "포인트가 부족합니다");
            }

            return "redirect:/alert";
        } else {
            redirectAttributes.addAttribute("string", "로그인 되지 않았습니다");
            return "redirect:/alert";
        }
    }

    @PostMapping("/Valorant_Exchange")
    @ApiOperation(value = "MyPoint -> Valorant 게임로 환전 API",
            notes = "MyPoint를 Valorant 게임으로 환전하는 API입니다. ExchangeDto를 통해서 데이터를 받아옵니다. (로그인 확인 시 사용되는 cookie는 숨기겠습니다.)")
    public String ValorantExchange(ExchangeDto exchangeDto,@ApiIgnore @CookieValue("users") Optional<Cookie> cookie,@ApiIgnore RedirectAttributes redirectAttributes) {
        if (cookie.isPresent()) {
            Users users = userService.findById(cookie.get().getValue()).get();

            if (StringUtils.isEmpty(exchangeDto.getEmail())) {
                redirectAttributes.addAttribute("string", "이메일을 입력하세요");
                return "redirect:/alert";
            }

            if (StringUtils.isEmpty(exchangeDto.getPassword())) {
                redirectAttributes.addAttribute("string", "비밀번호를 입력하세요");
                return "redirect:/alert";
            }

            if (exchangeDto.getPoint().isEmpty()) {
                redirectAttributes.addAttribute("string", "포인트를 입력하세요");
                return "redirect:/alert";
            }

            if (exchangeDto.getPoint().get() <= users.getPoint()) {
                userService.insertUsersPointHistory(users, exchangeDto, UsingType.CHARGE, PlusOrMinus.MINUS, "발로란트에 캐쉬로 환전");
                String s = "발로란트 게임으로 " + exchangeDto.getPoint().get() + " 포인트 환전 되었습니다";
                redirectAttributes.addAttribute("string", s);
            } else {
                redirectAttributes.addAttribute("string", "포인트가 부족합니다");
            }

            return "redirect:/alert";
        } else {
            redirectAttributes.addAttribute("string", "로그인 되지 않았습니다");
            return "redirect:/alert";
        }
    }

    @PostMapping("/STARCRAFT_Exchange")
    @ApiOperation(value = "MyPoint -> STARCRAFT 게임으로 환전 API",
            notes = "MyPoint를 STARCRAFT 게임으로 환전하는 API입니다. ExchangeDto를 통해서 데이터를 받아옵니다. (로그인 확인 시 사용되는 cookie는 숨기겠습니다.)")
    public String STARCRAFTExchange(ExchangeDto exchangeDto,@ApiIgnore @CookieValue("users") Optional<Cookie> cookie,@ApiIgnore RedirectAttributes redirectAttributes) {
        if (cookie.isPresent()) {
            Users users = userService.findById(cookie.get().getValue()).get();

            if (StringUtils.isEmpty(exchangeDto.getEmail())) {
                redirectAttributes.addAttribute("string", "이메일을 입력하세요");
                return "redirect:/alert";
            }

            if (StringUtils.isEmpty(exchangeDto.getPassword())) {
                redirectAttributes.addAttribute("string", "비밀번호를 입력하세요");
                return "redirect:/alert";
            }

            if (exchangeDto.getPoint().isEmpty()) {
                redirectAttributes.addAttribute("string", "포인트를 입력하세요");
                return "redirect:/alert";
            }


            if (exchangeDto.getPoint().get() <= users.getPoint()) {
                userService.insertUsersPointHistory(users, exchangeDto, UsingType.CHARGE, PlusOrMinus.MINUS, "스타크래프트에 캐쉬로 환전");
                String s = "스타크래프트 게임으로 " + exchangeDto.getPoint().get() + " 포인트 환전 되었습니다";
                redirectAttributes.addAttribute("string", s);
            } else {
                redirectAttributes.addAttribute("string", "포인트가 부족합니다");
            }

            return "redirect:/alert";
        } else {
            redirectAttributes.addAttribute("string", "로그인 되지 않았습니다");
            return "redirect:/alert";
        }
    }

    @PostMapping("/LOSTARK_Exchange")
    @ApiOperation(value = "MyPoint -> LOSTARK 게임으로 환전 API",
            notes = "MyPoint를 LOSTARK 게임으로 환전하는 API입니다. ExchangeDto를 통해서 데이터를 받아옵니다. (로그인 확인 시 사용되는 cookie는 숨기겠습니다.)")
    public String LOSTARKExchange(ExchangeDto exchangeDto,@ApiIgnore @CookieValue("users") Optional<Cookie> cookie,@ApiIgnore RedirectAttributes redirectAttributes) {
        if (cookie.isPresent()) {
            Users users = userService.findById(cookie.get().getValue()).get();

            if (StringUtils.isEmpty(exchangeDto.getEmail())) {
                redirectAttributes.addAttribute("string", "이메일을 입력하세요");
                return "redirect:/alert";
            }

            if (StringUtils.isEmpty(exchangeDto.getPassword())) {
                redirectAttributes.addAttribute("string", "비밀번호를 입력하세요");
                return "redirect:/alert";
            }

            if (exchangeDto.getPoint().isEmpty()) {
                redirectAttributes.addAttribute("string", "포인트를 입력하세요");
                return "redirect:/alert";
            }


            if (exchangeDto.getPoint().get() <= users.getPoint()) {
                userService.insertUsersPointHistory(users, exchangeDto, UsingType.CHARGE, PlusOrMinus.MINUS, "로스트 아크에 캐쉬로 환전");
                String s = "로스트아크 게임으로 " + exchangeDto.getPoint().get() + " 포인트 환전 되었습니다";
                redirectAttributes.addAttribute("string", s);
            } else {
                redirectAttributes.addAttribute("string", "포인트가 부족합니다");
            }

            return "redirect:/alert";
        } else {
            redirectAttributes.addAttribute("string", "로그인 되지 않았습니다");
            return "redirect:/alert";
        }
    }

    @PostMapping("/MAPLESTORY_Exchange")
    @ApiOperation(value = "MyPoint -> MAPLESTORY 게임으로 환전 API",
            notes = "MyPoint를 MAPLESTORY 게임으로 환전하는 API입니다. ExchangeDto를 통해서 데이터를 받아옵니다. (로그인 확인 시 사용되는 cookie는 숨기겠습니다.)")
    public String MAPLESTORYExchange(ExchangeDto exchangeDto,@ApiIgnore @CookieValue("users") Optional<Cookie> cookie,@ApiIgnore RedirectAttributes redirectAttributes) {
        if (cookie.isPresent()) {
            Users users = userService.findById(cookie.get().getValue()).get();

            if (StringUtils.isEmpty(exchangeDto.getPassword())) {
                redirectAttributes.addAttribute("string", "비밀번호를 입력하세요");
                return "redirect:/alert";
            }

            if (exchangeDto.getPoint().isEmpty()) {
                redirectAttributes.addAttribute("string", "포인트를 입력하세요");
                return "redirect:/alert";
            }

            if (StringUtils.isEmpty(exchangeDto.getEmail())) {
                redirectAttributes.addAttribute("string", "이메일을 입력하세요");
                return "redirect:/alert";
            }

            if (exchangeDto.getPoint().get() <= users.getPoint()) {
                userService.insertUsersPointHistory(users, exchangeDto, UsingType.CHARGE, PlusOrMinus.MINUS, "메이플스토리에 캐쉬로 환전");
                String s = "메이플스토리 게임으로 " + exchangeDto.getPoint().get() + " 포인트 환전 되었습니다";
                redirectAttributes.addAttribute("string", s);
            } else {
                redirectAttributes.addAttribute("string", "포인트가 부족합니다");
            }

            return "redirect:/alert";
        } else {
            redirectAttributes.addAttribute("string", "로그인 되지 않았습니다");
            return "redirect:/alert";
        }
    }
}
