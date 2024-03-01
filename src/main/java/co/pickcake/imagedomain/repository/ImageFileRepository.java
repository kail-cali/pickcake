package co.pickcake.imagedomain.repository;

import co.pickcake.imagedomain.entity.ProfileImage;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ImageFileRepository {

    private final EntityManager em;


    public Long save(ProfileImage profileImage) {
        em.persist(profileImage);
        return profileImage.getId();
    }

    public ProfileImage findById(Long id) {
        return em.find(ProfileImage.class, id);
    }


}
