package My_Project.integration.repository.UserCustom.Impl;

import My_Project.integration.entity.Users;
import My_Project.integration.repository.UserCustom.UserCustomRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final EntityManager em;

    @Override
    public boolean duplicateCheck(Users users) {
        Optional<Users> users1 = Optional.of(em.find(Users.class, users.getId()));
        Optional<Users> users2 = Optional.of(em.find(Users.class, users.getSsn()));
        Optional<Users> users3 = Optional.of(em.find(Users.class, users.getPhoneNumber()));

        if(users1.isPresent() || users2.isPresent() || users3.isPresent()) {
            return false; // 실패
        }
        return true; // 성공
    }
}
