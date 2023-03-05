package My_Project.integration.repository.PhotoCustom.impl;

import My_Project.integration.entity.Photo;
import My_Project.integration.entity.QPhoto;
import My_Project.integration.repository.PhotoCustom.PhotoCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import static My_Project.integration.entity.QPhoto.*;

public class PhotoCustomRepositoryImpl implements PhotoCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public PhotoCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public void deletePhotoSetByPostInfoId(Long postNumber) {
        jpaQueryFactory
                .delete(photo)
                .where(photo.postInfo.postNumber.eq(postNumber))
                .execute();
    }
}
