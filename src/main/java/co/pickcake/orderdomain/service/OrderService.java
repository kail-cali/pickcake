package co.pickcake.orderdomain.service;

import co.pickcake.authdomain.repository.MemberRepository;
import co.pickcake.orderdomain.entity.Delivery;
import co.pickcake.orderdomain.entity.DeliveryStatus;
import co.pickcake.orderdomain.entity.Order;
import co.pickcake.orderdomain.entity.OrderItem;
import co.pickcake.orderdomain.entity.item.Item;
import co.pickcake.orderdomain.repository.ItemRepository;
import co.pickcake.orderdomain.repository.OrderRepository;
import co.pickcake.authdomain.entity.Member;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /* api */

    /*
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setStatus(DeliveryStatus.READY);
        delivery.setAddress(member.getAddress());

        // 주문상품 생성  -> form ??
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장 -> cascade 고민하기!
        //cascade -> private 오너일 경우에만 사용하기
        // 쓰지 않을 경우 별도의 레포지토리 생성하고 별도로 persist 하기

        orderRepository.save(order);

        return order.getId();
    }

    /*
     * 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        order.cancel();
    }

    /*
     * 검색
     * */
//    public List<Order> findOrders(OrderSearch orderSearch) {
//        return orderSearch.findALL(orderSearch);
//
//    }







}
