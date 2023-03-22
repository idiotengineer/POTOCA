package My_Project.integration.repository.PhotoCustom.impl;

import My_Project.integration.repository.PhotoCustom.PhotoCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static My_Project.integration.entity.QPhoto.photo;

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
