package co.pickcake.orderdomain.repository;

import co.pickcake.orderdomain.entity.item.Cake;
import co.pickcake.orderdomain.entity.item.Item;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CakeRepository {

    private final EntityManager em;

    public void save(Cake item) {
        if (item.getId() == null ) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    public Cake findCakeById(Long id) {
        return em.find(Cake.class, id);
    }

    public Cake findById(Long id) {
        return em.find(Cake.class, id);
    }

    public List<Cake> findAll() {
        return em.createQuery("select i from Cake i", Cake.class)
                .getResultList();
    }

    public List<Cake> findByBrand(String brand) {
        return em.createQuery("select i from Cake i where i.brand = :brand", Cake.class)
                .setParameter("brand", brand)
                .getResultList();
    }


}
