package co.pickcake;


import co.pickcake.authdomain.entity.Member;

import co.pickcake.orderdomain.entity.Delivery;
import co.pickcake.orderdomain.entity.Order;
import co.pickcake.orderdomain.entity.OrderItem;
import co.pickcake.orderdomain.entity.item.Cake;
import co.pickcake.orderdomain.entity.item.CakeCategory;
import co.pickcake.orderdomain.entity.item.EventCakeCategory;

import co.pickcake.policies.policy.policy.FileUuidGeneratePolicy;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;


    @PostConstruct
    public void init() {
        initService.dbInitWithItem();
//        initService.dbInitWithOrder();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final FileUuidGeneratePolicy fileUuidGeneratePolicy;

        private Member createMember(String name, String city, String street, String zipcode) {
            return Member.createMember(name, city, street, zipcode);

        }
        public void dbInitWithItem() {

            Cake cake1 = Cake.createCakeWithImage("초코 케이크", "성심당", "우유 생크림이 들어간 케이크",
                    150000, fileUuidGeneratePolicy);


            CakeCategory cakeCategory1 = CakeCategory.createCategory("기념일");
            CakeCategory cakeCategory2 = CakeCategory.createCategory("발렌타인");
            CakeCategory cakeCategory3 = CakeCategory.createCategory("연인");
            em.persist(cakeCategory1);
            em.persist(cakeCategory2);
            em.persist(cakeCategory3);

            EventCakeCategory eventCakeCategory1 = EventCakeCategory.create(cake1,cakeCategory1);

            em.persist(cake1);







        }

        public void dbInitWithOrder() {
            Member member = createMember("userA", "서울", "테헤란로", "22101");
            em.persist(member);

            Cake cake1 = Cake.createCakeWithImage("생크림 케이크", "신라호텔", "우유 생크림이 들어간 케이크",
                    150000, fileUuidGeneratePolicy);

            em.persist(cake1);

            CakeCategory cakeCategory1 = CakeCategory.createCategory("생일");
            em.persist(cakeCategory1);


            EventCakeCategory eventCakeCategory1 = EventCakeCategory.create(cake1,cakeCategory1);

            em.persist(eventCakeCategory1);

            Cake cake2 = Cake.createCakeWithImage("딸기 가득 시루떡 케이크", "성심당", "초코 카스테라에 딸기가 가득 올라간 케이크",
                    210000, fileUuidGeneratePolicy);
            em.persist(cake2);

            CakeCategory cakeCategory2 = CakeCategory.createCategory("12월 크리스마스");
            em.persist(cakeCategory2);

            EventCakeCategory eventCakeCategory2 = EventCakeCategory.create(cake2,cakeCategory2);
            em.persist(eventCakeCategory2);

            EventCakeCategory eventCakeCategory3 = EventCakeCategory.create(cake2,cakeCategory1);
            em.persist(eventCakeCategory3);


            /* order items */
            OrderItem orderItem1 = OrderItem.createOrderItem(cake1, 1300000, 0);
            OrderItem orderItem2 = OrderItem.createOrderItem(cake2, 210000, 0);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());

            Order order = Order.createOrder(member, delivery, orderItem1);
            em.persist(order);
        }




    }
}


