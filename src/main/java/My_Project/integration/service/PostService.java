package My_Project.integration.service;

import My_Project.integration.entity.Dates;
import My_Project.integration.entity.Dto.PostInfoDto;
import My_Project.integration.entity.PostComments;
import My_Project.integration.entity.PostInfo;
import My_Project.integration.entity.Users;
import My_Project.integration.repository.PostRepository;
import My_Project.integration.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import javax.persistence.GeneratedValue;
import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UsersRepository usersRepository;

    public boolean Posting(@CookieValue(name = "users")Cookie cookie, PostInfoDto postInfoDto) {
        Optional<Users> usersByEmail = usersRepository.findUsersByEmail(cookie.getValue());
        if (usersByEmail.isPresent()) {
            PostInfo postInfo = new PostInfo(usersByEmail.get(),postInfoDto);
            postRepository.save(postInfo);
            return true;
        }
        return false;
    }
}
