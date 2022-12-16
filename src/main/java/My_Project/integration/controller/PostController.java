package My_Project.integration.controller;

import My_Project.integration.entity.Dto.PostInfoDto;
import My_Project.integration.entity.PostInfo;
import My_Project.integration.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/post_execute")
    public String postExecute(@CookieValue(name = "users") Cookie cookie, PostInfoDto postInfoDto, Model model, MultipartFile file) {
        if (postService.Posting(cookie, postInfoDto)) {
            model.addAttribute("string","일반 게시글 작성이 완료되었습니다.");
        } else {
            model.addAttribute("string","실패하였습니다");
        }
        return "/alert";
    }
}
