package My_Project.integration.repository.UserCustom;

import My_Project.integration.entity.Users;
import My_Project.integration.repository.UsersRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCustomRepository {

    boolean duplicateCheck(Users users);
}
