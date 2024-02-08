package co.pickcake.imagedomain.repository;

import co.pickcake.imagedomain.entity.ImageFile;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ImageFileRepository {

    private final EntityManager em;


    public Long save(ImageFile imageFile) {
        em.persist(imageFile);
        return imageFile.getId();
    }

    public ImageFile findById(Long id) {
        return em.find(ImageFile.class, id);
    }


}
