package My_Project.integration.entity.ResponseDto;

import My_Project.integration.entity.Photo;
import lombok.Getter;

@Getter
public class PhotoResponseDto {
    private String filePath;

    public PhotoResponseDto(Photo photo) {
        this.filePath = photo.getFilePath();
    }
}
