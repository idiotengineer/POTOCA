package My_Project.integration.repository.BigCommentsRepositorCustom;

import My_Project.integration.entity.BigComments;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BigCommentsCustomRepository {

    public Optional<BigComments> findBigCommentsById(Long id);

    public List<BigComments> findBigCommentsByUsersEmailList(List<String> usersEmailList);

    public long deleteBigCommentsListByUsersEmailList(List<String> userEmailList);
}
