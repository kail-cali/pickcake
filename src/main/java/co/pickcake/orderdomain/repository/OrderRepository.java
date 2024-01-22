package co.pickcake.orderdomain.repository;

import co.pickcake.orderdomain.entity.Order;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);

    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }


    public List<Order> findAllDefault(OrderSearch orderSearch) {
        return em.createQuery("select o from Order o join o.member m" +
                " where o.orderStatus = :status " +
                " and m.username like :name", Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
                .setMaxResults(1000)
                .getResultList();
    }
//    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
//
//    }



}
