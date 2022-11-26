package My_Project.integration.service;

import My_Project.integration.entity.Users;
import My_Project.integration.repository.UsersRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Getter
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Transactional
    public Users addUsers(Users users) {
        if (!usersRepository.duplicateCheck(users)) {
            Exception e = new Exception("중복된 값이 검출됨");
            e.
        } else {
            usersRepository.save(users);
            System.out.println("Users 엔티티를 저장하였습니다.");
            return users;
        }
    }
}
