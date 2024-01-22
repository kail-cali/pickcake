package co.pickcake.orderdomain.repository;

import co.pickcake.common.entity.SearchLevel;
import co.pickcake.orderdomain.entity.item.Cake;
import jakarta.persistence.EntityManager;
import lombok.Builder;
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

    /*##NEEDTOFIX##
    * TEST 용 Mock object 가 사용할 메서드만 남기고 수정 권한은 ADMIN 으로 옮기기
    * */
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

//    public List<Cake> findByCategory(String categoryName, SearchLevel searchLevel) {
//        return em.createQuery("select i from Cake i where i.cakeCategoryList = :categoryName",
//                Cake.class)
//                .setParameter(cakeCategoryList,categoryName)
//                .getResultList();
//
//    }


}
