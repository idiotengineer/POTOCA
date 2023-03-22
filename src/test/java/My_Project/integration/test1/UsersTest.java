package My_Project.integration.test1;

import My_Project.integration.entity.Address;
import My_Project.integration.entity.Dates;
import My_Project.integration.entity.Users;
import My_Project.integration.repository.UsersRepository;
import My_Project.integration.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;

@SpringBootTest
@Transactional
@DisplayName("회원 도메인 테스트")
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
        Users users = new Users("abc123", "1234", "홍길동", "01012345678", "0010063912631", address, 1L, null, null, dates,new HashSet<>(),new HashSet<>());
        //when
     /*   Users user1 = userService.addUsers(users);
        //then
        Assertions.assertThat(user1.getId()).isEqualTo("abc123");*/
    }

    @Test
    public void 중복회원테스트1() {
        //given id, ssn, phoneNumber가 중복되는 insert
        Address address1 = new Address("서울시", "강남구", "테헤란로", "129", "5층");
        Dates dates1 = new Dates(LocalDateTime.now(), LocalDateTime.now());
        Users users1 = new Users("abc123", "1234", "홍길동", "01012345678", "0010063912631", address1, 1L, null, null, dates1,new HashSet<>(),new HashSet<>());

        Address address2 = new Address("서울시", "강남구", "테헤란로", "129", "5층");
        Dates dates2 = new Dates(LocalDateTime.now(), LocalDateTime.now());
        Users users2 = new Users("abc123", "1234", "홍길동", "01012345678", "0010063912631", address2, 1L, null, null, dates2,new HashSet<>(),new HashSet<>());

        //when
        try {
            userService.addUsers(users2);
            userService.addUsers(users1);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.assertThat(1).isOne();
            return;
        }
        //then
        Assertions.fail("실패");
    }

    @Test
    public void 중복회원테스트2() {
        //given id값만 같은 User 등록 시도
        Address address1 = new Address("서울시", "강남구", "테헤란로", "129", "5층");
        Dates dates1 = new Dates(LocalDateTime.now(), LocalDateTime.now());
        Users users1 = new Users("abc123", "1234", "홍길동", "01011111111", "0123456789012", address1, 1L, null, null, dates1,new HashSet<>(),new HashSet<>());

        Address address2 = new Address("서울시", "강남구", "테헤란로", "129", "5층");
        Dates dates2 = new Dates(LocalDateTime.now(), LocalDateTime.now());
        Users users2 = new Users("abc123", "1234", "홍길동", "01012345678", "0111111111111", address2, 1L, null, null, dates2,new HashSet<>(),new HashSet<>());

        //when 등록시도
        try {
            userService.addUsers(users2);
            userService.addUsers(users1);
        }


        //then
        catch (Exception e) {
            e.printStackTrace();
            Assertions.assertThat(1).isOne();
            return;
        }

        Assertions.fail("실패");
    }

    @Test
    public void 중복회원테스트3() {
        //given ssn값만 같은 User 등록 시도
        Address address1 = new Address("서울시", "강남구", "테헤란로", "129", "5층");
        Dates dates1 = new Dates(LocalDateTime.now(), LocalDateTime.now());
        Users users1 = new Users("abc1234", "1234", "홍길동", "01011111111", "123456", address1, 1L, null, null, dates1,new HashSet<>(),new HashSet<>());

        Address address2 = new Address("서울시", "강남구", "테헤란로", "129", "5층");
        Dates dates2 = new Dates(LocalDateTime.now(), LocalDateTime.now());
        Users users2 = new Users("abc123", "1234", "홍길동", "01012345678", "123456", address2, 1L, null, null, dates2,new HashSet<>(),new HashSet<>());

        //when 등록시도
        try {
            userService.addUsers(users2);
            userService.addUsers(users1);
        }


        //then
        catch (Exception e) {
            e.printStackTrace();
            Assertions.assertThat(1).isOne();
            return;
        }

        Assertions.fail("실패");
    }

    @Test
    public void 중복회원테스트4() {
        //given phoneNumber값만 같은 User 등록 시도
        Address address1 = new Address("서울시", "강남구", "테헤란로", "129", "5층");
        Dates dates1 = new Dates(LocalDateTime.now(), LocalDateTime.now());
        Users users1 = new Users("abc1234", "1234", "홍길동", "01012345678", "123456", address1, 1L, null, null, dates1,new HashSet<>(),new HashSet<>());

        Address address2 = new Address("서울시", "강남구", "테헤란로", "129", "5층");
        Dates dates2 = new Dates(LocalDateTime.now(), LocalDateTime.now());
        Users users2 = new Users("abc123", "1234", "홍길동", "01012345678", "123423456", address2, 1L, null, null, dates2,new HashSet<>(),new HashSet<>());

        //when 등록시도
        try {
            userService.addUsers(users2);
            userService.addUsers(users1);
        }


        //then
        catch (Exception e) {
            e.printStackTrace();
            Assertions.assertThat(1).isOne();
            return;
        }

        Assertions.fail("실패");
    }

    @Test
    public void 중복회원테스트5() {
        //given 중복되는 값이 없는 객체삽입
        Address address1 = new Address("서울시", "강남구", "테헤란로", "129", "5층");
        Dates dates1 = new Dates(LocalDateTime.now(), LocalDateTime.now());
        Users users1 = new Users("abc1234", "1234", "홍길동", "01012345678", "123456", address1, 1L, null, null, dates1,new HashSet<>(),new HashSet<>());

        Address address2 = new Address("서울시", "강남구", "테헤란로", "129", "5층");
        Dates dates2 = new Dates(LocalDateTime.now(), LocalDateTime.now());
        Users users2 = new Users("abc123", "1234", "홍길동", "01066666666", "123456421", address2, 1L, null, null, dates2,new HashSet<>(),new HashSet<>());

        //when 등록시도
        try {
            userService.addUsers(users2);
            userService.addUsers(users1);
        }
        //then
        catch (Exception e) {
            e.printStackTrace();
            Assertions.assertThat(1).isOne();
            Assertions.fail("실패");
        }
        return;
    }
}
