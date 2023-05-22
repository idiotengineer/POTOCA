package My_Project.integration.RepositoryTest;

import My_Project.integration.repository.UsersRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class UsersRepositoryTest {

    @Autowired
    UsersRepository usersRepository;

    @Test
    public void deleteUserListTest() throws Exception {
        //given
        List<String> userEmailList = new ArrayList<>();
        userEmailList.add("donkey1072@naver.com");

        //when
        long l = usersRepository.deleteUserList(userEmailList);
        //then
    }
}
