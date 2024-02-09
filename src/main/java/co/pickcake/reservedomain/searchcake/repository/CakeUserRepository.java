package co.pickcake.reservedomain.searchcake.repository;

import co.pickcake.common.entity.QCategory;
import co.pickcake.imagedomain.entity.QCakeImages;
import co.pickcake.reservedomain.entity.item.Cake;
import co.pickcake.reservedomain.entity.item.QCake;
import co.pickcake.reservedomain.searchcake.dto.CakeSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CakeUserRepository {
    /*
    *  CAKE USER REPO 스펙 및 제공 API -- TODO
    * - 전체 케이크 상품 조회
    * - 브랜드별 상품 조회
    * - (NEEDTOFIX) 시즌카테고리별 상품 조회
    *
    * - TODO 랭킹 상품 조회 -> service layer
    *
    * - TODO 해쉬태그 상품 조회 -> service layer
    * */

    private final EntityManager em;

    public Cake findById(Long id) {
        return em.find(Cake.class, id);
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
    public List<Cake> findByBrand(int offset,int limit, String brand) {
        return em.createQuery(
                     "select c from Cake c " +
                        " join fetch c.cakeImages ci" +

                        " where c.brand = :brand", Cake.class)
                .setParameter("brand", brand)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    /* NEEDLEPOINT & TODO
    * entity 조회이긴 한데 화면 종속적인 파라미터 서치가 있어서 refactoring 추후 필요 */
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
