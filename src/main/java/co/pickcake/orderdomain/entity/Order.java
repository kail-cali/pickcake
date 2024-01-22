package co.pickcake.orderdomain.entity;

import co.pickcake.authdomain.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static co.pickcake.aop.util.ErrorCode.COULD_NOT_CANCEL_FOR_CAMPED_ITEM;

@Entity @Getter
@Table(name="orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    /* ##NEEDTOFIX## */
    @OneToMany(mappedBy ="order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;  //

    private LocalDateTime orderDate; // 주문 시각 -> 정책 확인 ##NEEDTOFIX##

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    /* 수정 메서드 */
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
    public void setMember(Member member) {
        this.member = member;
    }


    /* 연관관계 편의 메서드 */

//    public void setMember(Member member) {
//        this.member = member;
//        member.getOrders().add(this);
//    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItemList.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }


    /* 생성 메서드 */
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem: orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.MADE_ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    /* 비즈니스 로직 */
    public void cancel() {
        if (delivery.getStatus() !=  DeliveryStatus.READY) {
            throw new IllegalStateException(COULD_NOT_CANCEL_FOR_CAMPED_ITEM.toString());
        }
        this.setOrderStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItemList) {
            orderItem.cancel();
        }

    }

    /* 조회 로직 */

    public int getTotalPrice() {
        return orderItemList.stream()
                .mapToInt(OrderItem:: getTotalPrice)
                .sum();
    }

}
