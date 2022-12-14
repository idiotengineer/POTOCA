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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.servlet.http.Cookie;
import javax.transaction.Transactional;
import java.util.ArrayList;
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

        throw new NoSuchElementException("postInfo ????????? ?????? ??? ????????????");
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
        // ?????? ????????? ?????? Board ?????? ??????
        Optional<Users> usersByEmail = usersRepository.findUsersByEmail(cookie.getValue());
        if (usersByEmail.isPresent()) {
            PostInfo postInfo = new PostInfo(usersByEmail.get(), postInfoDto);
            postRepository.save(postInfo);
        } else {
            throw new Exception("???????????? ?????? ???????????????");
        }

        PostInfo postInfo = new PostInfo(
                usersByEmail.get(), postInfoDto
        );
        List<Photo> photoList = fileHandler.parseFileInfo(files);

        // ????????? ????????? ????????? ??????
        if (!photoList.isEmpty()) {
            for (Photo photo : photoList) {
                // ????????? DB??? ??????
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
    ) throws Exception {
        try {
            // ?????? ????????? ?????? Board ?????? ??????
            Optional<Users> usersByEmail = usersRepository.findUsersByEmail(cookie.getValue());
            PostInfo postInfo = new PostInfo(usersByEmail.get(), postInfoDto);
            List<Photo> photoList = fileHandler.parseFileInfo(files);

            // ????????? ????????? ????????? ??????
            if (!photoList.isEmpty()) {
                for (Photo photo : photoList) {
                    // ????????? DB??? ??????
                    postInfo.addPhoto(photoRepository.save(photo));
                }
            }

            postRepository.save(postInfo);

            PostDto postDto = new PostDto(postInfo);
            return postDto;
        } catch (NoSuchElementException e) {
            throw new Exception("???????????? ?????? ???????????????");
        }
    }


    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public PostInfoResponseDto searchById(Long id, List<Long> fileId) {
        PostInfo postInfo = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("?????? ???????????? ???????????? ????????????"));

        return new PostInfoResponseDto(postInfo, fileId);
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
            throw new NoSuchElementException("????????? ???????????? ????????????");
        }
    }

    @Transactional
    public List<PostDto> search(String keyword) {
        List<PostInfo> postInfo = postRepository.findByPostTitleContaining(keyword);
        List<PostDto> postDto = postInfo.stream()
                .map(postInfo1 -> new PostDto(postInfo1))
                .collect(Collectors.toList());

        return postDto;
    }

    @Transactional
    public Page<PostDto> getPostInfoList(Pageable pageable) {
        Page<PostInfo> all = postRepository.findAll(pageable);
        List<PostDto> collect = all.stream().map(
                postInfo -> new PostDto(postInfo)
        ).collect(Collectors.toList());

        /*int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page??? index ?????? 0?????? ??????
        pageable= PageRequest.of(page,10);*/

        Page<PostDto> postDtos = new PageImpl<>(collect, all.getPageable(), all.getTotalElements());
        return postDtos;
    }

    @Transactional
    public Page<PostDto> SearchByName(String name,Pageable pageable) {
        List<PostDto> list = postRepository.searchByName(name);

        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        Page<PostDto> postDtos = new PageImpl<>(list.subList(start, end), pageable, list.size());

        return postDtos;
    }

    @Transactional
    public Page<PostDto> SearchByTitle(String title,Pageable pageable) {
        List<PostDto> list = postRepository.searchByTitle(title);

        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        Page<PostDto> postDtos = new PageImpl<>(list.subList(start, end), pageable, list.size());

        return postDtos;
    }
}
