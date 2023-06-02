package My_Project.integration.controller;

import My_Project.integration.entity.Dto.ListingPostDto;
import My_Project.integration.entity.Dto.PostDto;
import My_Project.integration.service.PostService;
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
public class ListPageController {

    private final Logger LOGGER = LoggerFactory.getLogger(ListPageController.class);
    @Autowired
    PostService postService;

    @ApiOperation(value = "일반 게시글 리스트 페이지 접속")
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

   /* @GetMapping("/search_title")
    public String searchByTitle() {

    }*/

    @GetMapping("/search_user")
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
    }


    /*@GetMapping("/searchPage")
    public String searchPage(@ApiIgnore Model model, @PageableDefault Pageable pageable, HttpServletRequest request)
    {
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
            Page<PostDto> list = (Page<PostDto>) flashMap.get("list");
            model.addAttribute("list",list);
        return "listpage_copy2";
    }*/

    @ApiOperation(value = "LOL 갤러리 리스팅 페이지")
    @GetMapping("/LeagueOfLegends_listpage")
    public String lolListPage(Model model,HttpServletRequest request, @PageableDefault Pageable pageable) {
        LOGGER.info("LOL 갤러리 리스팅 페이지 접속");

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null){
//            Page<PostDto> list = (Page<PostDto>) flashMap.get("list");
            Page<ListingPostDto> list = (Page<ListingPostDto>) flashMap.get("list");
            model.addAttribute("list",list);
        } else {
            LOGGER.info("리스트페이지 접속");
//            Page<PostDto> postDtoList = postService.getPostInfoList(pageable);
            Page<ListingPostDto> postDtoList = postService.getPostInfoListV2(pageable,"LEAGUEOFLEGEND");
            model.addAttribute("list", postDtoList);
        }

        return "listpage_LeagueOfLegends";
    }

    @ApiOperation(value = "VALORANT 갤러리 리스팅 페이지")
    @GetMapping("/Valorant_listpage")
    public String valorantListPage(Model model,HttpServletRequest request, @PageableDefault Pageable pageable) {
        LOGGER.info("VaLoRanT 갤러리 리스팅 페이지 접속");

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null){
//            Page<PostDto> list = (Page<PostDto>) flashMap.get("list");
            Page<ListingPostDto> list = (Page<ListingPostDto>) flashMap.get("list");
            model.addAttribute("list",list);
        } else {
            LOGGER.info("리스트페이지 접속");
//            Page<PostDto> postDtoList = postService.getPostInfoList(pageable);
            Page<ListingPostDto> postDtoList = postService.getPostInfoListV2(pageable,"VALORANT");
            model.addAttribute("list", postDtoList);
        }
        return "listpage_valorant";
    }

    @ApiOperation(value = "lostArk 갤러리 리스팅 페이지")
    @GetMapping("/LostArk_listpage")
    public String lostArkListPage(Model model,HttpServletRequest request, @PageableDefault Pageable pageable) {
        LOGGER.info("lostArk 갤러리 리스팅 페이지 접속");

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null){
//            Page<PostDto> list = (Page<PostDto>) flashMap.get("list");
            Page<ListingPostDto> list = (Page<ListingPostDto>) flashMap.get("list");
            model.addAttribute("list",list);
        } else {
            LOGGER.info("리스트페이지 접속");
//            Page<PostDto> postDtoList = postService.getPostInfoList(pageable);
            Page<ListingPostDto> postDtoList = postService.getPostInfoListV2(pageable,"LOSTARK");
            model.addAttribute("list", postDtoList);
        }
        return "listpage_lostArk";
    }

    @ApiOperation(value = "mapleStory 갤러리 리스팅 페이지")
    @GetMapping("/MapleStory_listpage")
    public String mapleStoryListPage(Model model,HttpServletRequest request, @PageableDefault Pageable pageable) {
        LOGGER.info("mapleStory 갤러리 리스팅 페이지 접속");

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null){
//            Page<PostDto> list = (Page<PostDto>) flashMap.get("list");
            Page<ListingPostDto> list = (Page<ListingPostDto>) flashMap.get("list");
            model.addAttribute("list",list);
        } else {
            LOGGER.info("리스트페이지 접속");
//            Page<PostDto> postDtoList = postService.getPostInfoList(pageable);
            Page<ListingPostDto> postDtoList = postService.getPostInfoListV2(pageable,"MAPLESTORY");
            model.addAttribute("list", postDtoList);
        }

        return "listpage_mapleStory";
    }

    @ApiOperation(value = "starCraft 갤러리 리스팅 페이지")
    @GetMapping("/StarCraft_listpage")
    public String starCraftListPage(Model model,HttpServletRequest request, @PageableDefault Pageable pageable) {
        LOGGER.info("starCraft 갤러리 리스팅 페이지 접속");

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null){
//            Page<PostDto> list = (Page<PostDto>) flashMap.get("list");
            Page<ListingPostDto> list = (Page<ListingPostDto>) flashMap.get("list");
            model.addAttribute("list",list);
        } else {
            LOGGER.info("리스트페이지 접속");
//            Page<PostDto> postDtoList = postService.getPostInfoList(pageable);
//            Page<ListingPostDto> postDtoList = postService.getPostInfoList(pageable);
            Page<ListingPostDto> postDtoList = postService.getPostInfoListV2(pageable, "STARCRAFT");
            model.addAttribute("list", postDtoList);
        }
        return "listpage_starCraft";
    }

    @GetMapping("/search_user/RegularPost")
    public String searchByUserRegularPost(@RequestParam(value = "string") String string,@PageableDefault Pageable pageable, Model model) {
        Page<PostDto> list = postService.SearchByNameV2(string, pageable, "Regular");
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","user");
        return "listpage_copy";
    }

    @GetMapping("/search_title/RegularPost")
    public String searchByTitleRegularPost(@RequestParam(value = "string") String string,@PageableDefault Pageable pageable,Model model) {
        Page<PostDto> list = postService.SearchByTitleV2(string, pageable,"Regular");
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","title");
        return "listpage_copy";
    }

    @GetMapping("/search_user/LOLPost")
    public String searchByUserLOLPost(@RequestParam(value = "string") String string,@PageableDefault Pageable pageable, Model model) {
        Page<PostDto> list = postService.SearchByNameV2(string, pageable, "LEAGUEOFLEGEND");
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","user");
        return "listpage_LeagueOfLegends";
    }

    @GetMapping("/search_title/LOLPost")
    public String searchByTitleLOLPost(@RequestParam(value = "string") String string,@PageableDefault Pageable pageable,Model model) {
        Page<PostDto> list = postService.SearchByTitleV2(string, pageable,"LEAGUEOFLEGEND");
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","title");
        return "listpage_LeagueOfLegends";
    }

    @GetMapping("/search_user/LostArkPost")
    public String searchByUserLostArkPost(@RequestParam(value = "string") String string,@PageableDefault Pageable pageable, Model model) {
        Page<PostDto> list = postService.SearchByNameV2(string, pageable, "LOSTARK");
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","user");
        return "listpage_lostArk";
    }

    @GetMapping("/search_title/LostArkPost")
    public String searchByTitleLostArkPost(@RequestParam(value = "string") String string,@PageableDefault Pageable pageable,Model model) {
        Page<PostDto> list = postService.SearchByTitleV2(string, pageable,"LOSTARK");
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","title");
        return "listpage_lostArk";
    }

    @GetMapping("/search_user/MapleStoryPost")
    public String searchByUserMapleStoryPost(@RequestParam(value = "string") String string,@PageableDefault Pageable pageable, Model model) {
        Page<PostDto> list = postService.SearchByNameV2(string, pageable, "MAPLESTORY");
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","user");
        return "listpage_mapleStory";
    }

    @GetMapping("/search_title/MapleStoryPost")
    public String searchByTitleMapleStoryPost(@RequestParam(value = "string") String string,@PageableDefault Pageable pageable,Model model) {
        Page<PostDto> list = postService.SearchByTitleV2(string, pageable,"MAPLESTORY");
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","title");
        return "listpage_mapleStory";
    }

    @GetMapping("/search_user/StarCraftPost")
    public String searchByUserStarCraftPost(@RequestParam(value = "string") String string,@PageableDefault Pageable pageable, Model model) {
        Page<PostDto> list = postService.SearchByNameV2(string, pageable, "STARCRAFT");
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","user");
        return "listpage_starcraft";
    }

    @GetMapping("/search_title/StarCraftPost")
    public String searchByTitleStarCraftPost(@RequestParam(value = "string") String string,@PageableDefault Pageable pageable,Model model) {
        Page<PostDto> list = postService.SearchByTitleV2(string, pageable,"STARCRAFT");
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","title");
        return "listpage_starcraft";
    }

    @GetMapping("/search_user/ValorantPost")
    public String searchByUserValorantPost(@RequestParam(value = "string") String string,@PageableDefault Pageable pageable, Model model) {
        Page<PostDto> list = postService.SearchByNameV2(string, pageable, "VALORANT");
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","user");
        return "listpage_valorant";
    }

    @GetMapping("/search_title/ValorantPost")
    public String searchByTitleValorantPost(@RequestParam(value = "string") String string,@PageableDefault Pageable pageable,Model model) {
        Page<PostDto> list = postService.SearchByTitleV2(string, pageable,"VALORANT");
        model.addAttribute("list", list);
        model.addAttribute("keyword",string);
        model.addAttribute("how_to_search","title");
        return "listpage_valorant";
    }
}
