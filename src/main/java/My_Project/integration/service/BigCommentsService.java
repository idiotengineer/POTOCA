package My_Project.integration.service;

import My_Project.integration.entity.BigComments;
import My_Project.integration.repository.BigCommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BigCommentsService {

    @Autowired
    BigCommentsRepository bigCommentsRepository;

    public Optional<BigComments> findBigCommentsById(Long id) {
        return bigCommentsRepository.findBigCommentsById(id);
    }
}
