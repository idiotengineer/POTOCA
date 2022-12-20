package My_Project.integration.service;

import My_Project.integration.entity.Dto.PhotoDto;
import My_Project.integration.entity.ResponseDto.PhotoResponseDto;
import My_Project.integration.entity.Photo;
import My_Project.integration.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PhotoService {
    private final PhotoRepository photoRepository;

    @Transactional(readOnly = true)
    public PhotoDto findByFileId(Long id) {
        Photo photo = photoRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 파일이 존재하지 않습니다"));

        PhotoDto photoDto = PhotoDto.builder()
                .origFileName(photo.getOrigFileName())
                .filePath(photo.getFilePath())
                .fileSize(photo.getFileSize())
                .build();

        return photoDto;
    }

    @Transactional(readOnly = true)
    public List<PhotoResponseDto> findAllByPostInfo(Long postNumber) {
        List<Photo> photoList = photoRepository.findAllByPostInfo_PostNumber(postNumber);

        return photoList.stream()
                .map(PhotoResponseDto::new)
                .collect(Collectors.toList());
    }
}
