package co.pickcake.shopdomain.repository;


import co.pickcake.shopdomain.entity.Shop;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ShopRepository {

    private final EntityManager em;

    /* admin */
    public void save(Shop shop) {
        em.persist(shop);
    }

    public Shop findById(Long id) {
        return em.find(Shop.class, id);
    }

    public List<Shop> findByName(String shopName) {

        return em.createQuery(
                     "select s from Shop s" +
                        " where s.shopName = : shopName", Shop.class)
                .setParameter("shopName", shopName)
                .getResultList();
    }



}
