package co.pickcake.imagedomain.repository;


import co.pickcake.imagedomain.entity.CakeImages;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;



@Repository
@RequiredArgsConstructor
public class CakeImageRepository {

    private final EntityManager em;
    public Long save(CakeImages cakeImages) {
        em.persist(cakeImages);
        return cakeImages.getId();
    }

    public CakeImages findById(Long id) {
        return em.find(CakeImages.class, id);
    }



}
