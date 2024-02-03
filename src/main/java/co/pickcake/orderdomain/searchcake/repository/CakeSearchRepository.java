package co.pickcake.orderdomain.searchcake.repository;


import co.pickcake.orderdomain.entity.item.EventCakeCategory;
import co.pickcake.orderdomain.searchcake.dto.CakeCategorySearch;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CakeSearchRepository {

    private final EntityManager em;

    private List<CakeCategorySearch> findCake() {
        return em.createQuery("select new co.pickcake.orderdomain.searchcake.dto.CakeCategorySearch(c.id, c.name,c.imagePath,c.price,c.brand)" +
                        " from Cake c"
                        , CakeCategorySearch.class)

                .getResultList();
    }

    public List<EventCakeCategory> findBySingleCategory(int offset, int limit, String categoryName) {
        return em.createQuery(
                "select distinct e from EventCakeCategory e" +
                        " join fetch e.cake c" +
                        " join fetch e.cakeCategory cc" +
                        " where cc.name = :name", EventCakeCategory.class)
                .setParameter("name", categoryName)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }


}
