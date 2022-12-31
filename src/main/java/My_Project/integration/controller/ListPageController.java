package My_Project.integration.controller;

import My_Project.integration.entity.Dto.PostDto;
import My_Project.integration.entity.Dto.PostInfoDto;
import My_Project.integration.entity.PostInfo;
import My_Project.integration.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ListPageController {

    @Autowired
    PostService postService;

    @GetMapping("/postInfo")
    public String search(String keyword, Model model) {
        List<PostDto> postDto = postService.search(keyword);
        model.addAttribute("",postDto)
    }
}
