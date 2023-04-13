package My_Project.integration.repository.BigCommentsRepositorCustom;

import My_Project.integration.entity.BigComments;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BigCommentsCustomRepository {

    public Optional<BigComments> findBigCommentsById(Long id);
}
