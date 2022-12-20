package My_Project.integration.entity.ResponseDto;

import My_Project.integration.entity.Photo;
import lombok.Getter;

@Getter
public class PhotoResponseDto {
    private Long fileId;

    public PhotoResponseDto(Photo photo) {
        this.fileId = photo.getId();
    }
}
