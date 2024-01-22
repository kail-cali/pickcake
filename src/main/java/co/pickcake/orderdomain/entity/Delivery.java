package co.pickcake.orderdomain.entity;

import co.pickcake.common.entity.Address;
import jakarta.persistence.*;
import lombok.Getter;



@Entity
@Getter

/* delivery event relation */
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;



    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    public void setOrder(Order order) {
        this.order = order;
    }


}
