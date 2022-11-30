package My_Project.integration.repository;

import My_Project.integration.entity.Users;
import My_Project.integration.repository.UserCustom.UserCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long>, UserCustomRepository {}
