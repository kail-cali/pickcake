package co.pickcake.orderdomain.entity.item;

import co.pickcake.common.entity.Address;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    private int price;
    private int stockQuantity;

}
