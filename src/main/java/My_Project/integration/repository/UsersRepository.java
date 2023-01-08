package My_Project.integration.repository;

import My_Project.integration.entity.Dto.PostDto;
import My_Project.integration.entity.Users;
import My_Project.integration.repository.UserCustom.UserCustomRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,String>, UserCustomRepository {

    Optional<Users> findByEmailAndPassword(String email,String password);

    Optional<Users> findUsersByPhoneNumberAndName(String PhoneNumber, String Name);

    Optional<Users> findUsersByPhoneNumberAndEmail(String PhoneNumber, String Email);

    Optional<Users> findUsersByEmail(String s);
}
