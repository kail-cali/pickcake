package co.pickcake.orderdomain.service;

import co.pickcake.aop.util.ErrorCode;
import co.pickcake.aop.util.exception.NotEnoughStockException;
import co.pickcake.authdomain.entity.Member;
import co.pickcake.common.entity.Address;
import co.pickcake.orderdomain.entity.Order;
import co.pickcake.orderdomain.entity.OrderStatus;
import co.pickcake.orderdomain.entity.item.Cake;
import co.pickcake.orderdomain.entity.item.Item;
import co.pickcake.orderdomain.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    @DisplayName("")
    public void createOrder() throws Exception {

        //given
        // 유저 등록
        Member member = createMember();

//        Callable<Object> callable = new Callable<Object>() {
//            public Object call() throws Exception {
//
//            }
//        };
        // 상품 등록 -> admin 단에서 추후 구현 필요
        Cake cake = createCake("생크림 케이크", 150000, 3);

        //when
        int orderCount = 1;
        Long orderId = orderService.order(member.getId(), cake.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.MADE_ORDER, getOrder.getOrderStatus(), "상품 주문 시 상태는 MADE_ORDER");
        assertEquals(1, getOrder.getOrderItemList().size(), "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(2, cake.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");
        assertEquals( 150000, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다. ");


    }

    private Cake createCake(String productName, int price, int quantity) {
        Cake cake = new Cake();
        cake.setName(productName);
        cake.setPrice(price);
        cake.setStockQuantity(quantity);
        em.persist(cake);
        return cake;
    }

    private Member createMember() {
        Member member = new Member();
        member.setUsername("user1");
        member.setAddress(Address.createAddress("seoul", "abs", "123"));
        em.persist(member);
        return member;
    }

    @Test
    @DisplayName("")
    public void cancelOrder() throws Exception {
        //given
        Member member =createMember();
        Cake cake = createCake("생크림 케이크", 120000, 3);
        int orderCount = 1;
        Long orderId = orderService.order(member.getId(), cake.getId(), orderCount);
        //when

        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getOrderStatus(), "주문 취소 시 상태는 CANCEL이 된다.");
        assertEquals(3, cake.getStockQuantity(),  "주문 취소된 상품의 재고는 다시 원복한다.");


    }
    @Test
    @DisplayName("")
    public void createOrderExceptStock() throws Exception {
        //given
        // 유저 등록
        Member member = createMember();

//        Callable<Object> callable = new Callable<Object>() {
//            public Object call() throws Exception {
//
//            }
//        };
        // 상품 등록 -> admin 단에서 추후 구현 필요
        Cake cake = createCake("딸기 생크림 케이크", 60000, 1);

        //when
        int orderCount = 5;
        NotEnoughStockException thrownError =
                assertThrows(NotEnoughStockException.class,
                        () -> orderService.order(member.getId(), cake.getId(), orderCount));

        //then
        org.assertj.core.api.Assertions.assertThat(thrownError.getMessage())
                .isEqualTo(ErrorCode.NOT_ENOUGH_STOCK.toString());

    }



}