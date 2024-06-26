package co.pickcake.reservation.searchcake.repository;

import co.pickcake.common.entity.QCategory;
import co.pickcake.image.domain.QCakeImages;
import co.pickcake.reservation.domain.item.Cake;
import co.pickcake.reservation.domain.item.QCake;
import co.pickcake.reservation.searchcake.dto.CakeSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Deprecated
@Repository
@RequiredArgsConstructor
public class CakeUserRepository {
    /*
    Depreciate
    * */

    private final EntityManager em;

    public Cake findById(Long id) {
        return em.find(Cake.class, id);
    }
    public Cake findByIdDetails(Long id) {
        return em.createQuery(
                "select c from Cake c" +
                        " join fetch c.cakeImages ci" +
                        " join fetch ci.profileImage p" +
                        " join fetch c.shop s" +
                        " join fetch s.reserveInfo ri" +
                        " join fetch s.naver sn" +
                        " join fetch s.instagram sii" +
                        " where c.id = :itemId", Cake.class)
                .setParameter("itemId", id).getSingleResult();
    }
    public List<Cake> findAll(int offset, int limit) {
        return em.createQuery(
                         "select c from Cake c" +
                            " join fetch c.cakeImages ci"

                        , Cake.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
    public List<Cake> findByBrand(String brand) {
        return em.createQuery(
                             "select c from Cake c" +
                                " join fetch c.cakeImages ci" +
                                " where c.brand = :brand", Cake.class)
                .setParameter("brand", brand)
                .getResultList();
    }
    public List<Cake> findByBrand(int offset, int limit, String brand) {
        return em.createQuery(
                     "select c from Cake c " +
                        " join fetch c.cakeImages ci" +
                        " where c.brand = :brand", Cake.class)
                .setParameter("brand", brand)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Cake> findByCategory(CakeSearch cakeSearch) {
        QCake cake = QCake.cake;
        QCategory qCategory = QCategory.category;
        QCakeImages qCakeImages = QCakeImages.cakeImages;
        JPAQueryFactory query = new JPAQueryFactory(em);
        return query
                .select(cake)
                .from(cake)
                .fetchJoin()
                .where(cake.brand.eq(cakeSearch.getBrand()))
                .limit(1000)
                .fetch();
    }
    public List<Cake> findByNameOnLike(int offset, int limit, String cakeName) {
        return em.createQuery(
                     "select c from Cake c" +
                        " join fetch c.cakeImages ci" +
                        " where c.name " +
                        " like concat('%', :cakeName, '%')", Cake.class)
                .setParameter("cakeName", cakeName)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

}
