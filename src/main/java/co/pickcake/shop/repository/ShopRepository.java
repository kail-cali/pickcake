package co.pickcake.shop.repository;


import co.pickcake.shop.domain.Shop;
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
    public List<Shop> findAll(int offset, int limit) {
        return em.createQuery(
                "select s from Shop s" +
                        " join fetch s.reserveInfo sr" +
                        " join fetch s.naver n"
                        , Shop.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
    public Shop findByIdWithDetail(Long id) {
        return em.createQuery(
                "select s from Shop s" +
                        " join fetch s.naver sn" +
                        " join fetch s.instagram si" +
                        " join fetch s.reserveInfo sr" +
                        " where s.id = : id", Shop.class)
                .setParameter("id", id)
                .getResultList()
                .getFirst();
    }
}
