package co.pickcake.orderdomain.searchcake.repository;

import co.pickcake.common.entity.QCategory;
import co.pickcake.orderdomain.entity.item.Cake;
import co.pickcake.orderdomain.entity.item.QCake;
import co.pickcake.orderdomain.searchcake.dto.CakeSearch;
import com.querydsl.core.types.dsl.BooleanExpression;
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

    public List<Cake> findAll() {
        return em.createQuery(
                "select c from Cake c", Cake.class)
                .getResultList();
    }

    public List<Cake> findAll(int offset, int limit) {
        return em.createQuery(
                        "select c from Cake c", Cake.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Cake> findByBrand(String brand) {
        return em.createQuery("select i from Cake i where i.brand = :brand", Cake.class)
                .setParameter("brand", brand)
                .getResultList();
    }

    public List<Cake> findByBrand(int offset,int limit, String brand) {
        return em.createQuery("select i from Cake i where i.brand = :brand", Cake.class)
                .setParameter("brand", brand)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }



    public List<Cake> findByCategory(CakeSearch cakeSearch) {
        QCake cake = QCake.cake;
        QCategory qCategory = QCategory.category;
        JPAQueryFactory query = new JPAQueryFactory(em);
        return query
                .select(cake)
                .from(cake)
                .where(cake.brand.eq(cakeSearch.getBrand()))
                .limit(1000)
                .fetch();
    }





//    private BooleanExpression

//    public List<Cake> findAll(CakeSearch cakeSearch) {
//        QCake cake = QCake.cake;
//        QCategory qCategory = QCategory.category;
//
//        JPAQueryFactory query = new JPAQueryFactory(em);
//        query
//                .select(cake)
//                .from(cake)
//
//
//    }



}
