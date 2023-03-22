package My_Project.integration.service;

import My_Project.integration.entity.*;
import My_Project.integration.entity.Dto.*;
import My_Project.integration.entity.ResponseDto.PostInfoResponseDto;
import My_Project.integration.entity.ResponseDto.PostLikeAndDislikeDto;
import My_Project.integration.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.Transient;
import javax.servlet.http.Cookie;
import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.*;
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
    private final PostLikeAndDislikeRepository postLikeAndDislikeRepository;

    @Autowired
    private final EntityManager em;


    @Autowired
    private final FileHandler fileHandler;
    @Autowired
    private final PhotoRepository photoRepository;

    @Autowired
    private final LikedRepository likedRepository;

    @Autowired
    private final DisLikedRepository disLikedRepository;

    public PostInfo findPost(Long id) throws NoSuchElementException {
//        Optional<PostInfo> postInfo = postRepository.findPostInfoByPostNumber(id);
        Optional<PostInfo> postInfo = postRepository.findPostByIdWithFetchJoinUsedQueryDSL(id);
        return postInfo.get();
    }

    public PostInfo findPostV2(Long id) throws NoSuchElementException {
        Optional<PostInfo> postInfo = postRepository.findPostByIdWithFetchJoinUsedQueryDSLV2(id);
        return postInfo.get();
    }

    public PostDto getPostDto(Optional<PostInfo> postInfo) {
        if (postInfo.isPresent()) {
            PostDto postDto = new PostDto(postInfo.get());
            return postDto;
        }

        throw new NoSuchElementException("postInfo 객체를 찾을 수 없습니다");
    }

    public PostDto getPostDtoWithPostlidiDto(Optional<PostInfo> postInfo, PostLikeAndDislikeDto postLikeAndDislikeDto) {
        if (postInfo.isPresent()) {
            PostDto postDto = new PostDto(postInfo.get());
            return postDto;
        }

        throw new NoSuchElementException("postInfo 객체를 찾을 수 없습니다");
    }

    @Transactional
    public PostLikeAndDislikeDto getPostLikeAndDislike(Long id) {
        return new PostLikeAndDislikeDto(postLikeAndDislikeRepository.findPostLikeAndDislikeByPostInfoPostNumber(id));
    }

    public boolean Posting(@CookieValue(name = "users") Cookie cookie, PostInfoDto postInfoDto) {
        Optional<Users> usersByEmail = usersRepository.findUsersByEmail(cookie.getValue());
        if (usersByEmail.isPresent()) {

            PostLikeAndDislike postLikeAndDislike = new PostLikeAndDislike();
            if (!savePostLikeAndDislike(postLikeAndDislike)) {
                new Exception("PostLikeAndDislike 객체 생성 실패");
            }

            PostInfo postInfo = new PostInfo(usersByEmail.get(), postInfoDto, postLikeAndDislike);
            postRepository.save(postInfo);
            return true;
        }
        return false;
    }

    /*@Transactional
    public PostDto create1(
            PostInfoDto postInfoDto,
            List<MultipartFile> files,
            @CookieValue(name = "users") Cookie cookie
    ) throws Exception {
        // 파일 처리를 위한 Board 객체 생성
        Optional<Users> usersByEmail = usersRepository.findUsersByEmail(cookie.getValue());
        if (usersByEmail.isPresent()) {
            PostLikeAndDislike postLikeAndDislike = new PostLikeAndDislike();
            if (!savePostLikeAndDislike(postLikeAndDislike)) {
                throw new Exception("PostLikeAndDislike 객체 생성 실패");
            }
            PostInfo postInfo = new PostInfo(usersByEmail.get(), postInfoDto,postLikeAndDislike);
            postRepository.save(postInfo);
        } else {
            throw new Exception("존재하지 않는 회원입니다");
        }

        PostInfo postInfo = new PostInfo(
                usersByEmail.get(), postInfoDto,
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
    }*/

    @Transactional
    public PostDto create(
            PostInfoDto postInfoDto,
            List<MultipartFile> files,
            @CookieValue(name = "users") Cookie cookie
    ) throws Exception {
        try {
            // 파일 처리를 위한 Board 객체 생성
            Optional<Users> usersByEmail = usersRepository.findUsersByEmail(cookie.getValue());
            PostLikeAndDislike postLikeAndDislike = new PostLikeAndDislike();

            if (!savePostLikeAndDislike(postLikeAndDislike)) {
                new Exception("PostLikeAndDislike 객체 생성 실패");
            }
            PostInfo postInfo = new PostInfo(usersByEmail.get(), postInfoDto, postLikeAndDislike);
            savePhoto(files, postInfo);

            PostDto postDto = new PostDto(postInfo);
            return postDto;

        } catch (NoSuchElementException e) {
            throw new Exception("존재하지 않는 회원입니다");
        }
    }

    private void savePhoto(List<MultipartFile> files, PostInfo postInfo) throws Exception {
        List<Photo> photoList = fileHandler.parseFileInfo(files);

        // 파일이 존재할 때에만 처리
        if (!photoList.isEmpty()) {
            for (Photo photo : photoList) {
                // 파일을 DB에 저장
                postInfo.addPhoto(photoRepository.save(photo));
            }
        }

        postRepository.save(postInfo);
    }


    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public PostInfoResponseDto searchById(Long id, List<Long> fileId) {
        PostInfo postInfo = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다"));

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
            throw new NoSuchElementException("로그인 되어있지 않습니다");
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
    public Page<ListingPostDto> getPostInfoList(Pageable pageable) {
        Page<PostInfo> all = postRepository.findAllByOrderByPostNumber(pageable);
        /*List<PostDto> collect = all.stream().map(
                postInfo -> new PostDto(postInfo)
        ).collect(Collectors.toList());
*/
        List<ListingPostDto> collect = all.stream().map(
                postInfo -> new ListingPostDto(postInfo)
        ).collect(Collectors.toList());

        Page<ListingPostDto> postDtos = new PageImpl<>(collect, all.getPageable(), all.getTotalElements());
        return postDtos;
    }

    @Transactional
    public Page<PostDto> SearchByName(String name, Pageable pageable) {
        List<PostDto> list = postRepository.searchByName(name);

        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        Page<PostDto> postDtos = new PageImpl<>(list.subList(start, end), pageable, list.size());

        return postDtos;
    }

    @Transactional
    public Page<PostDto> SearchByTitle(String title, Pageable pageable) {
        List<PostDto> list = postRepository.searchByTitle(title);

        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        Page<PostDto> postDtos = new PageImpl<>(list.subList(start, end), pageable, list.size());

        return postDtos;
    }

    @Transactional
    public boolean savePostLikeAndDislike(PostLikeAndDislike postLikeAndDislike) {
        try {
            postLikeAndDislikeRepository.save(postLikeAndDislike);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Transactional
    public PostLikeAndDislikeDto findPostlidiDtoByPostNumber(Long id) {
        return new PostLikeAndDislikeDto(postLikeAndDislikeRepository.findPostLikeAndDislikeByPostInfoPostNumber(id));
    }

    @Transactional
    public PostLikeAndDislike findPostlidiByPostNumber(Long id) {
        return postLikeAndDislikeRepository.findPostLikeAndDislikeByPostInfoPostNumber(id);
    }

    @Transactional
    public String addUsersSet(PostLikeAndDislike postLikeAndDislike, Users users, String s) {
        if (likeOrNot(s)) {
            Liked liked = new Liked();
            liked.setUsers(users);
            liked.setPostLikeAndDislike(postLikeAndDislike);

            likedRepository.save(liked);
            return String.valueOf(postLikeAndDislike.getLiked().size() + 1);
        } else {
            DisLiked disLiked = new DisLiked();
            disLiked.setUsers(users);
            disLiked.setPostLikeAndDislike(postLikeAndDislike);

            disLikedRepository.save(disLiked);
            return String.valueOf(postLikeAndDislike.getDisLiked().size() + 1);
        }
    }

    @Transactional
    public String removeUsersSet(PostLikeAndDislike postLikeAndDislike, Users users, String s) {
        if (likeOrNot(s)) {
            likedRepository.deleteByPostLidiAndUsers(postLikeAndDislike, users);
            return String.valueOf(postLikeAndDislike.getLiked().size() - 1);
        } else {
            disLikedRepository.deleteByPostLidiAndUsers(postLikeAndDislike, users);
            return String.valueOf(postLikeAndDislike.getDisLiked().size() - 1);
        }
    }


    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public void deletePhoto(PostInfo postInfo) {
        fileHandler.deleteFile(postInfo);
    }

    @Transactional
    public void modifyingPost(PostInfo postInfo, ModifyDto modifyDto) {
        try {
            deletePhoto(postInfo);

            postInfo.getPhoto().clear();
            em.clear();

            PostInfo post = findPost(postInfo.getPostNumber());
            post.setPostTitle(modifyDto.getPostTitle());
            post.setPostContent(modifyDto.getPostContent());

            savePhoto(modifyDto.getFiles(), post);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void clear() {
        em.flush();
        em.clear();
    }



    @Transactional
    public Optional<Liked> findLikedByUsersAndPostLidi(PostLikeAndDislike postLikeAndDislike, Users users) {
        return likedRepository.findLikedByUsers(postLikeAndDislike, users);
    }

    @Transactional
    public Optional<DisLiked> findDisLikedByUsersAndPostLidi(PostLikeAndDislike postLikeAndDislike, Users users) {
        return disLikedRepository.findDisLikedByUsers(postLikeAndDislike, users);
    }

    public boolean likeOrNot(String s) {
        return s.equals("like") ? true : false;
    }

//    @Transactional
//    public boolean deleteLiked(Liked liked) {
//        try {
//            likedRepository.delete(liked);
//        } catch (Exception e) {
//
//        }
//    }
//
//    @Transactional
//    public boolean deleteDisLiked() {
//
//    }
}
