package My_Project.integration.controller;

import My_Project.integration.entity.Dto.ListingPostDto;
import My_Project.integration.entity.Dto.PostDto;
import My_Project.integration.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@Api(tags = {"리스팅 API"})
public class ListPageController {

    private final Logger LOGGER = LoggerFactory.getLogger(ListPageController.class);
    @Autowired
    PostService postService;

    @ApiOperation(
            value = "Regular 게시글 리스트 페이지 접속",
            notes = "Regular 게시글의 리스팅 페이지에 접속합니다. Pageable 파라미터가 필요합니다")
    @GetMapping("/Regular_listpage")
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
            Page<ListingPostDto> postDtoList = postService.getPostInfoListV2(pageable,"Regular");
            model.addAttribute("list", postDtoList);
        }
        return "listpage_copy";
    }

    /*@GetMapping("/search_user")
    @ApiOperation(value = "")
    public String searchByUser(
            @RequestParam(value = "dtype") String s,
            @RequestParam(value = "string") String string,
            @PageableDefault Pageable pageable, Model model) {
        Page<PostDto> list = postService.SearchByNameV2(string, pageable,s);
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","user");
        return "listpage_copy2";
    }

    @GetMapping("/search_title")
    public String searchByTitle(
            @RequestParam(value = "dtype") String s,
            @RequestParam(value = "string") String string,
            @PageableDefault Pageable pageable,Model model) {
        Page<PostDto> list = postService.SearchByTitleV2(string,pageable,s);
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","title");
        return "listpage_copy2";
    }*/


    /*@GetMapping("/searchPage")
    public String searchPage(@ApiIgnore Model model, @PageableDefault Pageable pageable, HttpServletRequest request)
    {
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
            Page<PostDto> list = (Page<PostDto>) flashMap.get("list");
            model.addAttribute("list",list);
        return "listpage_copy2";
    }*/

    @ApiOperation(value = "LOL 갤러리 리스팅 페이지",
            notes = "LOL 게시글의 리스팅 페이지에 접속합니다. Pageable 파라미터가 필요합니다")
    @GetMapping("/LeagueOfLegends_listpage")
    public String lolListPage(@ApiIgnore Model model, @ApiIgnore HttpServletRequest request,@PageableDefault Pageable pageable) {
        LOGGER.info("LOL 갤러리 리스팅 페이지 접속");

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null){
//            Page<PostDto> list = (Page<PostDto>) flashMap.get("list");
            Page<ListingPostDto> list = (Page<ListingPostDto>) flashMap.get("list");
            model.addAttribute("list",list);
        } else {
//            Page<PostDto> postDtoList = postService.getPostInfoList(pageable);
            Page<ListingPostDto> postDtoList = postService.getPostInfoListV2(pageable,"LEAGUEOFLEGEND");
            model.addAttribute("list", postDtoList);
        }

        return "listpage_LeagueOfLegends";
    }

    @ApiOperation(value = "VALORANT 갤러리 리스팅 페이지",
            notes = "VALORANT 게시글의 리스팅 페이지에 접속합니다. Pageable 파라미터가 필요합니다")
    @GetMapping("/Valorant_listpage")
    public String valorantListPage(@ApiIgnore Model model,@ApiIgnore HttpServletRequest request, @PageableDefault Pageable pageable) {
        LOGGER.info("VaLoRanT 갤러리 리스팅 페이지 접속");

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null){
//            Page<PostDto> list = (Page<PostDto>) flashMap.get("list");
            Page<ListingPostDto> list = (Page<ListingPostDto>) flashMap.get("list");
            model.addAttribute("list",list);
        } else {
//            Page<PostDto> postDtoList = postService.getPostInfoList(pageable);
            Page<ListingPostDto> postDtoList = postService.getPostInfoListV2(pageable,"VALORANT");
            model.addAttribute("list", postDtoList);
        }
        return "listpage_valorant";
    }

    @ApiOperation(value = "lostArk 갤러리 리스팅 페이지",
            notes = "LOSTARK 게시글의 리스팅 페이지에 접속합니다. Pageable 파라미터가 필요합니다")
    @GetMapping("/LostArk_listpage")
    public String lostArkListPage(@ApiIgnore Model model,@ApiIgnore HttpServletRequest request, @PageableDefault Pageable pageable) {
        LOGGER.info("lostArk 갤러리 리스팅 페이지 접속");

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null){
//            Page<PostDto> list = (Page<PostDto>) flashMap.get("list");
            Page<ListingPostDto> list = (Page<ListingPostDto>) flashMap.get("list");
            model.addAttribute("list",list);
        } else {
//            Page<PostDto> postDtoList = postService.getPostInfoList(pageable);
            Page<ListingPostDto> postDtoList = postService.getPostInfoListV2(pageable,"LOSTARK");
            model.addAttribute("list", postDtoList);
        }
        return "listpage_lostArk";
    }

    @ApiOperation(value = "mapleStory 갤러리 리스팅 페이지",
            notes = "mapleStory 게시글의 리스팅 페이지에 접속합니다. Pageable 파라미터가 필요합니다")
    @GetMapping("/MapleStory_listpage")
    public String mapleStoryListPage(@ApiIgnore Model model,@ApiIgnore HttpServletRequest request, @PageableDefault Pageable pageable) {
        LOGGER.info("mapleStory 갤러리 리스팅 페이지 접속");

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null){
//            Page<PostDto> list = (Page<PostDto>) flashMap.get("list");
            Page<ListingPostDto> list = (Page<ListingPostDto>) flashMap.get("list");
            model.addAttribute("list",list);
        } else {
//            Page<PostDto> postDtoList = postService.getPostInfoList(pageable);
            Page<ListingPostDto> postDtoList = postService.getPostInfoListV2(pageable,"MAPLESTORY");
            model.addAttribute("list", postDtoList);
        }

        return "listpage_mapleStory";
    }

    @ApiOperation(value = "starCraft 갤러리 리스팅 페이지",
            notes = "starCraft 게시글의 리스팅 페이지에 접속합니다. Pageable 파라미터가 필요합니다")
    @GetMapping("/StarCraft_listpage")
    public String starCraftListPage(Model model,HttpServletRequest request, @PageableDefault Pageable pageable) {
        LOGGER.info("starCraft 갤러리 리스팅 페이지 접속");

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null){
//            Page<PostDto> list = (Page<PostDto>) flashMap.get("list");
            Page<ListingPostDto> list = (Page<ListingPostDto>) flashMap.get("list");
            model.addAttribute("list",list);
        } else {
//            Page<PostDto> postDtoList = postService.getPostInfoList(pageable);
//            Page<ListingPostDto> postDtoList = postService.getPostInfoList(pageable);
            Page<ListingPostDto> postDtoList = postService.getPostInfoListV2(pageable, "STARCRAFT");
            model.addAttribute("list", postDtoList);
        }
        return "listpage_starCraft";
    }

    @GetMapping("/search_user/RegularPost")
    @ApiOperation(
            value = "Regular 갤러리 게시글을 작성자 이메일로 조회",
            notes = "Regular 갤러리 게시글을 게시자의 이메일로 조회하여 리스팅 페이지를 리턴합니다. (Pageable 메서드는 숨기겠습니다)")
    public String searchByUserRegularPost(@RequestParam(value = "string") String string,@ApiIgnore @PageableDefault Pageable pageable,@ApiIgnore Model model) {
        Page<PostDto> list = postService.SearchByNameV2(string, pageable, "Regular");
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","user");
        return "listpage_copy";
    }

    @GetMapping("/search_title/RegularPost")
    @ApiOperation(
            value = "Regular 갤러리 게시글을 제목으로 조회",
            notes = "Regular 갤러리 게시글을 게시글의 제목으로 조회하여 리스팅 페이지를 리턴합니다. (Pageable 메서드는 숨기겠습니다)")
    public String searchByTitleRegularPost(@RequestParam(value = "string") String string,@PageableDefault @ApiIgnore Pageable pageable,@ApiIgnore Model model) {
        Page<PostDto> list = postService.SearchByTitleV2(string, pageable,"Regular");
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","title");
        return "listpage_copy";
    }

    @GetMapping("/search_user/LOLPost")
    @ApiOperation(
            value = "LOL 갤러리 게시글을 작성자 이메일로 조회",
            notes = "LOL 갤러리 게시글을 게시자의 이메일로 조회하여 리스팅 페이지를 리턴합니다. (Pageable 메서드는 숨기겠습니다)")
    public String searchByUserLOLPost(@RequestParam(value = "string") String string,@PageableDefault @ApiIgnore Pageable pageable, @ApiIgnore Model model) {
        Page<PostDto> list = postService.SearchByNameV2(string, pageable, "LEAGUEOFLEGEND");
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","user");
        return "listpage_LeagueOfLegends";
    }

    @GetMapping("/search_title/LOLPost")
    @ApiOperation(
            value = "LOL 갤러리 게시글을 제목으로 조회",
            notes = "LOL 갤러리 게시글을 게시글의 제목으로 조회하여 리스팅 페이지를 리턴합니다. (Pageable 메서드는 숨기겠습니다)")
    public String searchByTitleLOLPost(@RequestParam(value = "string") String string,@PageableDefault @ApiIgnore Pageable pageable,@ApiIgnore Model model) {
        Page<PostDto> list = postService.SearchByTitleV2(string, pageable,"LEAGUEOFLEGEND");
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","title");
        return "listpage_LeagueOfLegends";
    }

    @GetMapping("/search_user/LostArkPost")
    @ApiOperation(
            value = "LOSTARK 갤러리 게시글을 작성자 이메일로 조회",
            notes = "LOSTARK 갤러리 게시글을 게시자의 이메일로 조회하여 리스팅 페이지를 리턴합니다. (Pageable 메서드는 숨기겠습니다)")
    public String searchByUserLostArkPost(@RequestParam(value = "string") String string,@PageableDefault @ApiIgnore Pageable pageable, @ApiIgnore Model model) {
        Page<PostDto> list = postService.SearchByNameV2(string, pageable, "LOSTARK");
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","user");
        return "listpage_lostArk";
    }

    @GetMapping("/search_title/LostArkPost")
    @ApiOperation(
            value = "LOSTARK 갤러리 게시글을 제목으로 조회",
            notes = "LOSTARK 갤러리 게시글을 게시글의 제목으로 조회하여 리스팅 페이지를 리턴합니다. (Pageable 메서드는 숨기겠습니다)")
    public String searchByTitleLostArkPost(@RequestParam(value = "string") String string,@ApiIgnore @PageableDefault Pageable pageable,@ApiIgnore Model model) {
        Page<PostDto> list = postService.SearchByTitleV2(string, pageable,"LOSTARK");
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","title");
        return "listpage_lostArk";
    }

    @GetMapping("/search_user/MapleStoryPost")
    @ApiOperation(
            value = "MAPLESTORY 갤러리 게시글을 작성자 이메일로 조회",
            notes = "MAPLESTORY 갤러리 게시글을 게시자의 이메일로 조회하여 리스팅 페이지를 리턴합니다. (Pageable 메서드는 숨기겠습니다)")
    public String searchByUserMapleStoryPost(@RequestParam(value = "string") String string,@ApiIgnore @PageableDefault Pageable pageable,@ApiIgnore Model model) {
        Page<PostDto> list = postService.SearchByNameV2(string, pageable, "MAPLESTORY");
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","user");
        return "listpage_mapleStory";
    }

    @GetMapping("/search_title/MapleStoryPost")
    @ApiOperation(
            value = "MAPLESTORY 갤러리 게시글을 제목으로 조회",
            notes = "MAPLESTORY 갤러리 게시글을 게시글의 제목으로 조회하여 리스팅 페이지를 리턴합니다. (Pageable 메서드는 숨기겠습니다)")
    public String searchByTitleMapleStoryPost(@RequestParam(value = "string") String string,@ApiIgnore @PageableDefault Pageable pageable,@ApiIgnore Model model) {
        Page<PostDto> list = postService.SearchByTitleV2(string, pageable,"MAPLESTORY");
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","title");
        return "listpage_mapleStory";
    }

    @GetMapping("/search_user/StarCraftPost")
    @ApiOperation(
            value = "STARCRAFT 갤러리 게시글을 작성자 이메일로 조회",
            notes = "STARCRAFT 갤러리 게시글을 게시자의 이메일로 조회하여 리스팅 페이지를 리턴합니다. (Pageable 메서드는 숨기겠습니다)")
    public String searchByUserStarCraftPost(@RequestParam(value = "string") String string,@ApiIgnore @PageableDefault Pageable pageable,@ApiIgnore Model model) {
        Page<PostDto> list = postService.SearchByNameV2(string, pageable, "STARCRAFT");
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","user");
        return "listpage_starcraft";
    }

    @GetMapping("/search_title/StarCraftPost")
    @ApiOperation(
            value = "STARCRAFT 갤러리 게시글을 제목으로 조회",
            notes = "STARCRAFT 갤러리 게시글을 게시글의 제목으로 조회하여 리스팅 페이지를 리턴합니다. (Pageable 메서드는 숨기겠습니다)")
    public String searchByTitleStarCraftPost(@RequestParam(value = "string") String string,@ApiIgnore @PageableDefault Pageable pageable,@ApiIgnore Model model) {
        Page<PostDto> list = postService.SearchByTitleV2(string, pageable,"STARCRAFT");
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","title");
        return "listpage_starcraft";
    }

    @GetMapping("/search_user/ValorantPost")
    @ApiOperation(
            value = "VALORANT 갤러리 게시글을 작성자 이메일로 조회",
            notes = "VALORANT 갤러리 게시글을 게시자의 이메일로 조회하여 리스팅 페이지를 리턴합니다. (Pageable 메서드는 숨기겠습니다)")
    public String searchByUserValorantPost(@RequestParam(value = "string") String string,@ApiIgnore @PageableDefault Pageable pageable, @ApiIgnore Model model) {
        Page<PostDto> list = postService.SearchByNameV2(string, pageable, "VALORANT");
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","user");
        return "listpage_valorant";
    }

    @GetMapping("/search_title/ValorantPost")
    @ApiOperation(
            value = "VALORANT 갤러리 게시글을 제목으로 조회",
            notes = "VALORANT 갤러리 게시글을 게시글의 제목으로 조회하여 리스팅 페이지를 리턴합니다. (Pageable 메서드는 숨기겠습니다)")
    public String searchByTitleValorantPost(@RequestParam(value = "string") String string,@ApiIgnore @PageableDefault Pageable pageable,@ApiIgnore Model model) {
        Page<PostDto> list = postService.SearchByTitleV2(string, pageable,"VALORANT");
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","title");
        return "listpage_valorant";
    }
}
