package co.pickcake;


import co.pickcake.authdomain.entity.Member;
import co.pickcake.common.entity.Address;
import co.pickcake.orderdomain.entity.Delivery;
import co.pickcake.orderdomain.entity.Order;
import co.pickcake.orderdomain.entity.OrderItem;
import co.pickcake.orderdomain.entity.item.Cake;
import co.pickcake.orderdomain.entity.item.CakeCategory;
import co.pickcake.orderdomain.entity.item.EventCakeCategory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        private Member createMember(String name, String city, String street, String zipcode) {
            return Member.createMember(name, city, street, zipcode);

        }
        public void dbInit1() {
            Member member = createMember("userG", "서울", "연세로", "31200");
            em.persist(member);

            Cake cake1 = Cake.createCake("고구마 크림 케이크", "시그니엘", "맛있는 케이크",
                    500000, 10,"~/images");
            em.persist(cake1);

            Cake cake2 = Cake.createCake("초코 딸기 생크림 케이크", "한스", "더 맛있는 케이크",
                    121000, 2,"~/images");
            em.persist(cake2);

            /* order items */
            OrderItem orderItem1 = OrderItem.createOrderItem(cake1, 500000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(cake2, 121000, 1);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = createMember("userA", "서울", "테헤란로", "22101");
            em.persist(member);

            Cake cake1 = Cake.createCake("생크림 케이크", "신라호텔", "우유 생크림이 올라간 케이크",
                    1300000, 20,"~/images");

            CakeCategory cakeCategory1 = CakeCategory.createCategory("생일");

            em.persist(cake1);
            em.persist(cakeCategory1);
            EventCakeCategory eventCakeCategory1 = EventCakeCategory.create(cake1,cakeCategory1);
            em.persist(eventCakeCategory1);



            Cake cake2 = Cake.createCake("시루떡 케이크", "성심당", "초코 카스테라에 딸기가 가득 올라간 케이크",
                    210000, 100,"~/images");
            em.persist(cake2);

            CakeCategory cakeCategory2 = CakeCategory.createCategory("12월 크리스마스");
            em.persist(cakeCategory2);

            EventCakeCategory eventCakeCategory2 = EventCakeCategory.create(cake2,cakeCategory2);
            em.persist(eventCakeCategory2);

            EventCakeCategory eventCakeCategory3 = EventCakeCategory.create(cake2,cakeCategory1);
            em.persist(eventCakeCategory3);


            /* order items */
            OrderItem orderItem1 = OrderItem.createOrderItem(cake1, 1300000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(cake2, 210000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }




    }
}


