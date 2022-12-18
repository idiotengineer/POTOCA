package My_Project.integration.service;

import My_Project.integration.entity.Dto.PostInfoDto;
import My_Project.integration.entity.Photo;
import My_Project.integration.entity.PostInfo;
import My_Project.integration.entity.Users;
import My_Project.integration.repository.PhotoRepository;
import My_Project.integration.repository.PostRepository;
import My_Project.integration.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UsersRepository usersRepository;

    private final FileHandler fileHandler;
    private final PhotoRepository photoRepository;

    public boolean Posting(@CookieValue(name = "users") Cookie cookie, PostInfoDto postInfoDto) {
        Optional<Users> usersByEmail = usersRepository.findUsersByEmail(cookie.getValue());
        if (usersByEmail.isPresent()) {
            PostInfo postInfo = new PostInfo(usersByEmail.get(), postInfoDto);
            postRepository.save(postInfo);
            return true;
        }
        return false;
    }

    @Transactional
    public Long create(
            PostInfoDto postInfoDto,
            List<MultipartFile> files,
            @CookieValue(name = "users") Cookie cookie
    ) throws Exception {
        // 파일 처리를 위한 Board 객체 생성
        Optional<Users> usersByEmail = usersRepository.findUsersByEmail(cookie.getValue());
        if (usersByEmail.isPresent()) {
            PostInfo postInfo = new PostInfo(usersByEmail.get(), postInfoDto);
            postRepository.save(postInfo);
        } else {
            throw new Exception("존재하지 않는 회원입니다");
        }

        PostInfo postInfo = new PostInfo(
                usersByEmail.get(), postInfoDto
        );
        List<Photo> photoList = fileHandler.parseFileInfo(files);

        // 파일이 존재할 때에만 처리
        if (!photoList.isEmpty()) {
            for (Photo photo : photoList) {
                // 파일을 DB에 저장
                postInfo.addPhoto(photoRepository.save(photo));
            }
        }

        return postRepository.save(postInfo).getPostNumber();
    }

}
