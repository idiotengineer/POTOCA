package My_Project.integration.controller;

import My_Project.integration.entity.Dto.PostDto;
import My_Project.integration.entity.Dto.PostInfoDto;
import My_Project.integration.entity.ResponseDto.PhotoResponseDto;
import My_Project.integration.entity.ResponseDto.PostInfoResponseDto;
import My_Project.integration.service.PhotoService;
import My_Project.integration.service.PostService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    PhotoService photoService;

    @ApiOperation(value = "일반 게시글 페이지")
    @GetMapping("/post123")
    public String post(@RequestParam("postnum") Long id, Model model) {
        try {
            PostDto post = postService.findPost(id);
            String time = getTimeDiffAndReturnElapsedTime(post.getDates().getUpdatedTime(), LocalDateTime.now());

            model.addAttribute("postDto", post);
            model.addAttribute("time", time);
            return "post";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("string", "에러 발생");
            return "alert";
        }
    }

    public String getTimeDiffAndReturnElapsedTime(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        long year = duration.getSeconds() / 31556926;
        long month = duration.getSeconds() / 2629800;
        long day = duration.getSeconds() / 86400;
        long hour = duration.getSeconds() / 3600;
        long minute = duration.getSeconds() / 60;
        if (year > 0) { //
            return year + "년 전";
        } else if (month > 0) {
            return month + "개월 전";
        } else if (day > 0) {
            return day + "일 전";
        } else if (hour > 0) {
            return hour + "시간 전";
        } else if (minute > 0) {
            return minute + "분 전";
        } else {
            return duration.getSeconds() + "초 전";
        }
    }

    @PostMapping("/post_execute")
    public String postExecute(@CookieValue(name = "users") Cookie cookie, PostInfoDto postInfoDto, Model model, MultipartFile file) {
        if (postService.Posting(cookie, postInfoDto)) {
            model.addAttribute("string", "일반 게시글 작성이 완료되었습니다.");
        } else {
            model.addAttribute("string", "실패하였습니다");
        }
        return "/alert";
    }

    @PostMapping("/post_execute2")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(
            @RequestPart(value = "image", required = false) List<MultipartFile> files,
            PostInfoDto postInfoDto,
            @CookieValue(name = "users") Cookie cookie,
            Model model) {
        try {
            PostDto postDto = postService.create(postInfoDto, files, cookie);

            List<PhotoResponseDto> photoResponseDtoList = photoService.findAllByPostInfo(postDto.getPostNumber());
            List<Long> photoId = new ArrayList<>();

            for (PhotoResponseDto photoResponseDto : photoResponseDtoList) {
                photoId.add(photoResponseDto.getFileId());
            }

            model.addAttribute("fileIdList",photoId);
            model.addAttribute("postDto", postDto);
            model.addAttribute("time", postDto.getDates().getUpdatedTime());
            return "/post";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("일반 게시글 작성에 실패하셨습니다");
            return "/alert";
        }
    }

    @GetMapping("/board/{id}")
    public PostInfoResponseDto searchById(@PathVariable Long id) {
        List<PhotoResponseDto> photoResponseDtoList =
                photoService.findAllByPostInfo(id);

        List<Long> photoId = new ArrayList<>();

        for (PhotoResponseDto photoResponseDto : photoResponseDtoList) {
            photoId.add(photoResponseDto.getFileId());
        }

        return postService.searchById(id,photoId);
    }
}

