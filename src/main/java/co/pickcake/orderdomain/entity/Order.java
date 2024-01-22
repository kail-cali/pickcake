package co.pickcake.orderdomain.entity;

import co.pickcake.authdomain.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@Table(name="orders")
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

//    public void setMember(Member member) {
//        this.member = member;
//        member.getOrders().add(this);
//    }

    /* 연관관계 편의 메서드 */
    public void addOrderItem(OrderItem orderItem) {
        orderItemList.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
