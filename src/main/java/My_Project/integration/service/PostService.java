package My_Project.integration.service;

import My_Project.integration.entity.Dto.CommentDto;
import My_Project.integration.entity.Dto.PostDto;
import My_Project.integration.entity.Dto.PostInfoDto;
import My_Project.integration.entity.Photo;
import My_Project.integration.entity.PostComments;
import My_Project.integration.entity.PostInfo;
import My_Project.integration.entity.ResponseDto.PostInfoResponseDto;
import My_Project.integration.entity.Users;
import My_Project.integration.repository.PhotoRepository;
import My_Project.integration.repository.PostCommentsRepository;
import My_Project.integration.repository.PostRepository;
import My_Project.integration.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.Cookie;
import javax.sql.DataSource;
import javax.transaction.Transaction;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final UsersRepository usersRepository;

    @Autowired
    private final PostCommentsRepository postCommentsRepository;

    @Autowired
    private final EntityManager em;


    @Autowired
    private final FileHandler fileHandler;
    @Autowired
    private final PhotoRepository photoRepository;

    public PostDto findPost(Long id) throws NoSuchElementException {
        Optional<PostInfo> postInfo = postRepository.findById(id);

        if (postInfo.isPresent()) {
            PostDto postDto = new PostDto(postInfo.get());
            return postDto;
        }

        throw new NoSuchElementException("postInfo 객체를 찾을 수 없습니다");
    }

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
    public PostDto create1(
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
        postRepository.save(postInfo);

        PostDto postDto = new PostDto(postInfo);
        return postDto;
    }

    @Transactional
    public PostDto create(
            PostInfoDto postInfoDto,
            List<MultipartFile> files,
            @CookieValue(name = "users") Cookie cookie
    ) throws Exception{
        try{
        // 파일 처리를 위한 Board 객체 생성
        Optional<Users> usersByEmail = usersRepository.findUsersByEmail(cookie.getValue());
        PostInfo postInfo = new PostInfo(usersByEmail.get(), postInfoDto);
        List<Photo> photoList = fileHandler.parseFileInfo(files);

            // 파일이 존재할 때에만 처리
            if (!photoList.isEmpty()) {
                for (Photo photo : photoList) {
                    // 파일을 DB에 저장
                    postInfo.addPhoto(photoRepository.save(photo));
                }
            }

            postRepository.save(postInfo);

            PostDto postDto = new PostDto(postInfo);
            return postDto;
        } catch (NoSuchElementException e) {
            throw new Exception("존재하지 않는 회원입니다");
        }
    }


    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public PostInfoResponseDto searchById(Long id,List<Long> fileId) {
        PostInfo postInfo = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다"));

        return new PostInfoResponseDto(postInfo,fileId);
    }


    @Transactional
    public Boolean registerComments(CommentDto commentDto, Optional<Cookie> cookie) {
        if (cookie.isPresent()) {
            PostComments postComments = new PostComments(commentDto);
            postCommentsRepository.save(postComments);
            Optional<PostInfo> post = postRepository.findById(commentDto.getPost_number());
            post.get().getComments().add(postComments);

            em.flush();
            em.clear();
            return true;
        } else {
            throw new NoSuchElementException("로그인 되어있지 않습니다");
        }
    }

    @Transactional
    public List<PostDto> search(String keyword) {
        List<PostInfo> postInfo = postRepository.findByTitleContaining(keyword);
        List<PostDto> postDto = postInfo.stream()
                .map(postInfo1 -> new PostDto(postInfo1))
                .collect(Collectors.toList());

        return postDto;
    }
}
