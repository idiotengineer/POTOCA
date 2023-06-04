package My_Project.integration.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@Api(tags = "에러 API")
public class ErrorPageController {

    private final Logger LOGGER = LoggerFactory.getLogger(ErrorPageController.class);

    @RequestMapping("/error-page/404")
    @ApiOperation(value = "404 에러 API")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("errorPage 404");
        return "error/404";
    }

    @RequestMapping("/error-page/500")
    @ApiOperation(value = "500 에러 API")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("errorPage 500");
        return "error/500";
    }
}
