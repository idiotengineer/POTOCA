package My_Project.integration.test1;

import My_Project.integration.entity.Address;
import My_Project.integration.entity.Dates;
import My_Project.integration.entity.Users;
import My_Project.integration.repository.UsersRepository;
import My_Project.integration.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@SpringBootTest
@Transactional
public class UsersTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    UserService userService;

    @Test
    public void DB작동테스트() {
        //given
        Address address = new Address("서울시", "강남구", "테헤란로", "129", "5층");
        Dates dates = new Dates(LocalDateTime.now(), LocalDateTime.now());
        Users users = new Users("abc123", "1234", "홍길동", "01012345678", "0010063912631", address, 1L, null, null, dates);
        //when
        Users user1 = userService.addUsers(users);
        //then
        Assertions.assertThat(user1.getId()).isEqualTo("abc123");
    }

    @Test
    public void 중복회원테스트() {
        //given
        Address address1 = new Address("서울시", "강남구", "테헤란로", "129", "5층");
        Dates dates1 = new Dates(LocalDateTime.now(), LocalDateTime.now());
        Users users1 = new Users("abc123", "1234", "홍길동", "01012345678", "0010063912631", address1, 1L, null, null, dates1);

        Address address2 = new Address("서울시", "강남구", "테헤란로", "129", "5층");
        Dates dates2 = new Dates(LocalDateTime.now(), LocalDateTime.now());
        Users users2 = new Users("abc123", "1234", "홍길동", "01012345678", "0010063912631", address2, 1L, null, null, dates2);

        //when
        try {
            userService.addUsers(users2);
            userService.addUsers(users1);
        } catch (Exception e) {
            System.out.println(e.toString());
            return;
        }
        //then
        Assertions.fail("실패");
    }
}
