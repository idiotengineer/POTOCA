package My_Project.integration.controller;

import My_Project.integration.entity.Dto.PostDto;
import My_Project.integration.entity.Dto.PostInfoDto;
import My_Project.integration.entity.PostInfo;
import My_Project.integration.service.PostService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Controller
public class ListPageController {

    private final Logger LOGGER = LoggerFactory.getLogger(ListPageController.class);
    @Autowired
    PostService postService;

    @GetMapping("/search_title")
    public String search(String keyword, Model model) {
        List<PostDto> postDto = postService.search(keyword);
        model.addAttribute("list",postDto);
        return "listpage_copy";
    }

    @ApiOperation(value = "일반 게시글 리스트 페이지 접속")
    @GetMapping("/listpage")
    public String listPage(@ApiIgnore Model model, @PageableDefault(size = 10) Pageable pageable) {
        LOGGER.info("리스트페이지 접속");
        Page<PostDto> postDtoList = postService.getPostInfoList(pageable);
        model.addAttribute("list", postDtoList);
        return "listpage_copy";
    }
}
