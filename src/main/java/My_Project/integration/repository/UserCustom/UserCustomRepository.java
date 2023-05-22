package My_Project.integration.repository.UserCustom;

import My_Project.integration.entity.Users;
import My_Project.integration.repository.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCustomRepository {

    boolean duplicateCheck(Users users);

    public Page<Users> findAllUsersWithPaging(Pageable pageable);

    public long deleteUserList(List<String> userEmailList);
}
