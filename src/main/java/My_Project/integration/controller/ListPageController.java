package My_Project.integration.controller;

import My_Project.integration.entity.Dto.ListingPostDto;
import My_Project.integration.entity.Dto.PostDto;
import My_Project.integration.service.PostService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ListPageController {

    private final Logger LOGGER = LoggerFactory.getLogger(ListPageController.class);
    @Autowired
    PostService postService;

    @ApiOperation(value = "일반 게시글 리스트 페이지 접속")
    @GetMapping("/listpage")
    public String listPage(@ApiIgnore Model model, @PageableDefault Pageable pageable, HttpServletRequest request)
    {
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null){
//            Page<PostDto> list = (Page<PostDto>) flashMap.get("list");
            Page<ListingPostDto> list = (Page<ListingPostDto>) flashMap.get("list");
            model.addAttribute("list",list);
        } else {
            LOGGER.info("리스트페이지 접속");
//            Page<PostDto> postDtoList = postService.getPostInfoList(pageable);
            Page<ListingPostDto> postDtoList = postService.getPostInfoList(pageable);
            model.addAttribute("list", postDtoList);
        }
        return "listpage_copy";
    }

   /* @GetMapping("/search_title")
    public String searchByTitle() {

    }*/

    @GetMapping("/search_user")
    public String searchByUser(@RequestParam(value = "string") String string,@PageableDefault Pageable pageable,Model model) {
        Page<PostDto> list = postService.SearchByName(string, pageable);
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","user");
        return "listpage_copy2";
    }

    @GetMapping("/search_title")
    public String searchByTitle(@RequestParam(value = "string") String string,@PageableDefault Pageable pageable,Model model) {
        Page<PostDto> list = postService.SearchByTitle(string, pageable);
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","title");
        return "listpage_copy2";
    }

    /*@GetMapping("/searchPage")
    public String searchPage(@ApiIgnore Model model, @PageableDefault Pageable pageable, HttpServletRequest request)
    {
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
            Page<PostDto> list = (Page<PostDto>) flashMap.get("list");
            model.addAttribute("list",list);
        return "listpage_copy2";
    }*/
}
