package My_Project.integration.controller;

import My_Project.integration.entity.Dto.PhotoDto;
import My_Project.integration.entity.Photo;
import My_Project.integration.service.PhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@RestController
@Api(tags = "사진 API")
public class PhotoController {

    @Autowired
    private final PhotoService photoService;

    /**
     * 이미지 개별 조회
     */
    @CrossOrigin
    @ApiOperation(value = "사진 조회 API",
    notes = "사진을 id값으로 하나하나 조회해오는 API입니다")
    @GetMapping(
            value = "/image/{id}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) throws IOException {
        PhotoDto photoDto = photoService.findByFileId(id);
        String absolutePath
                = new File("").getAbsolutePath() + File.separator + File.separator;
        String path = photoDto.getFilePath();

        InputStream imageStream = new FileInputStream(absolutePath + path);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();

        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }

}
