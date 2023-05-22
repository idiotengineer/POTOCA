package My_Project.integration.service;

import My_Project.integration.entity.Dto.ExchangeDto;
import My_Project.integration.entity.Dto.FindEmailDto;
import My_Project.integration.entity.Dto.FindPasswordDto;
import My_Project.integration.entity.Dto.LoginDto;
import My_Project.integration.entity.Enum.PlusOrMinus;
import My_Project.integration.entity.Enum.UsingType;
import My_Project.integration.entity.PointHistory;
import My_Project.integration.entity.QUsers;
import My_Project.integration.entity.Users;
import My_Project.integration.repository.PointRepository;
import My_Project.integration.repository.UsersRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static My_Project.integration.entity.QUsers.users;

@Service
@RequiredArgsConstructor
@Getter
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PointRepository pointRepository;

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
    public Optional<Users> login(LoginDto loginDto) throws Exception {
        return usersRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
    }

    @Transactional
    public Optional<Users> findEmail(FindEmailDto findEmailDto) {
        return usersRepository.findUsersByPhoneNumberAndName(findEmailDto.getPhone_number(), findEmailDto.getName());
    }

    @Transactional
    public Optional<Users> findPassword(FindPasswordDto findPasswordDto) {
        return usersRepository.findUsersByPhoneNumberAndEmail(findPasswordDto.getPhone_number2(), findPasswordDto.getEmail());
    }

    @Transactional
    public Optional<Users> findById(String s) {
        return usersRepository.findById(s);
    }

    @Transactional
    public void insertUsersPointHistory(Users users, ExchangeDto exchangeDto, UsingType usingType, PlusOrMinus plusOrMinus, String s) {
        PointHistory pointHistory = PointHistory.builder()
                .setUppedTime(LocalDateTime.now())
                .point(exchangeDto.getPoint())
                .userId(exchangeDto.getEmail())
                .usingType(usingType)
                .plusOrMinus(plusOrMinus)
                .content(s)
                .build();

        pointRepository.save(pointHistory);

        users.getPointHistories().add(pointHistory);

        if (plusOrMinus == PlusOrMinus.PLUS) {
            users.setPoint(users.getPoint() + exchangeDto.getPoint());
        } else {
            users.setPoint(users.getPoint() - exchangeDto.getPoint());
        }
    }

    @Transactional
    public Page<Users> findAllUsersWithPaging(Pageable pageable) {
        return usersRepository.findAllUsersWithPaging(pageable);
    }

    @Transactional
    public long deleteUserList(List<String> userEmailList) {
        return usersRepository.deleteUserList(userEmailList);
    }
}
