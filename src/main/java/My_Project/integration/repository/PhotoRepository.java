package My_Project.integration.repository;

import My_Project.integration.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findAllByPostInfo_PostNumber(Long postNumber);

    List<Photo> findAllByPostInfoPostNumber(Long postNumber);

    boolean deletePhotoByPostInfo(Long postNumber);
}
