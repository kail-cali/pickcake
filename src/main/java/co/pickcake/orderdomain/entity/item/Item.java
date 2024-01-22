package co.pickcake.orderdomain.entity.item;

import co.pickcake.aop.util.ErrorCode;
import co.pickcake.aop.util.exception.NotEnoughStockException;
import co.pickcake.common.entity.Address;
import jakarta.persistence.*;
import lombok.Getter;

import static co.pickcake.aop.util.ErrorCode.*;

@Entity
@Getter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    public void setName(String name) {

        this.name = name;
    }

    private String name;

    @Embedded
    private Address address;

    private int price;
    private int stockQuantity;

    public void addStock(int stockQuantity) {
        this.stockQuantity += stockQuantity;
    }

    public void removeStock(int stockQuantity) {
        if (this.stockQuantity < stockQuantity) {
            throw new NotEnoughStockException(NOT_ENOUGH_STOCK.toString());
        }
        this.stockQuantity -= stockQuantity;
    }


}
