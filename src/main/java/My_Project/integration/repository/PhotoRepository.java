package My_Project.integration.repository;

import My_Project.integration.entity.Photo;
import My_Project.integration.entity.PostInfo;
import My_Project.integration.repository.PhotoCustom.PhotoCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long>, PhotoCustomRepository {
    List<Photo> findAllByPostInfo_PostNumber(Long postNumber);

    List<Photo> findAllByPostInfoPostNumber(Long postNumber);


}
