package My_Project.integration.repository.PostInfoCustom.Impl;

import My_Project.integration.entity.Dto.PostDto;
import My_Project.integration.entity.PostInfo;
import My_Project.integration.repository.PostInfoCustom.PostInfoCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PostInfoCustomRepositoryImpl implements PostInfoCustomRepository {

    @Autowired
    private final EntityManager em;

    public List<PostDto> searchByName(String name) {
        List<PostInfo> resultList = em.createQuery("select p from PostInfo p where p.postedUser.email like concat('%',?1,'%')")
                .setParameter(1, name)
                .getResultList();

        return resultList.stream().map(
                postInfo -> new PostDto(postInfo)
        ).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchByTitle(String title) {
        List<PostInfo> list = em.createQuery("select p from PostInfo p where p.postTitle like concat('%',?1,'%')")
                .setParameter(1, title)
                .getResultList();

                return list.stream().map(p -> new PostDto(p))
                .collect(Collectors.toList());
    }
}
