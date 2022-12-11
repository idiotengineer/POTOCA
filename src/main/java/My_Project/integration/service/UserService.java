package My_Project.integration.service;

import My_Project.integration.entity.Dto.FindEmailDto;
import My_Project.integration.entity.Dto.FindPasswordDto;
import My_Project.integration.entity.Dto.LoginDto;
import My_Project.integration.entity.Users;
import My_Project.integration.repository.UsersRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Transactional
    public Users addUsers(Users users) throws Exception {
        if (!usersRepository.duplicateCheck(users)) {
            throw new Exception("중복된 값이 검출되었습니다.");
        } else {
            usersRepository.save(users);
            System.out.println("Users 엔티티를 저장하였습니다.");
            return users;
        }
    }

    @Transactional
    public Optional<Users> login(LoginDto loginDto) throws Exception{
        return usersRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
    }

    @Transactional
    public Optional<Users> findEmail(FindEmailDto findEmailDto){
        return usersRepository.findUsersByPhoneNumberAndName(findEmailDto.getPhone_number(), findEmailDto.getName());
    }

    @Transactional
    public Optional<Users> findPassword(FindPasswordDto findPasswordDto){
        return usersRepository.findUsersByPhoneNumberAndEmail(findPasswordDto.getPhone_number2(), findPasswordDto.getEmail());
    }
}
