package My_Project.integration.test1;

import My_Project.integration.entity.Dto.UserInfoDto;
import My_Project.integration.entity.Users;
import My_Project.integration.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class SignupTest {

    @Autowired
    UserService userService;

    @Test
    public void 회원가입테스트() {
        UserInfoDto userInfoDto = new UserInfoDto(
                "abc1234",
                "abc1",
                "hihi",
                "01012341234",
                "1234567891123",
                "Seoul",
                "non-hyeon-dong",
                "teheran-ro 22",
                "1234",
                "5 floor"
        );

        Users users = new Users(userInfoDto);

        try {
            userService.addUsers(users);
        } catch (Exception e) {
            Assertions.fail();
        }
    }
}
