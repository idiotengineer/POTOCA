package My_Project.integration.controller;

import My_Project.integration.entity.Dto.PostInfoDto;
import My_Project.integration.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    PostService postService;

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
            @RequestPart(value = "image",required = false) List<MultipartFile> files,
            PostInfoDto postInfoDto,
            @CookieValue(name = "users") Cookie cookie,
            Model model) {
        try {
            postService.create(postInfoDto, files, cookie);
            return "/post";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("일반 게시글 작성에 실패하셨습니다");
            return "/alert";
        }
    }
}
