package co.pickcake.reservedomain.entity.item;

import co.pickcake.aop.util.exception.NotEnoughStockException;
import co.pickcake.common.entity.Address;
import co.pickcake.recommand.entity.Heart;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static co.pickcake.aop.util.ErrorCode.*;

@Entity @Getter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name="item_id")
    private Long id;
    private String name;
    @Embedded
    private Address address;
    private int price;

    /* 수정 메서드 */
    public void setName(String name) {this.name = name;}
    public void setPrice(int price) {this.price = price;}

    public void setId(Long id) {this.id = id;}

    /* 추후 기능 추가 시 다시 추가 및 수정 예정 */

    //    /* api */
//    @OneToMany(mappedBy = "item")
//    private List<Heart> hearts = new ArrayList<>();

//    private int stockQuantity;
//public void setStockQuantity(int stockQuantity) {
//    this.stockQuantity = stockQuantity;
//}
//    /* 비즈니스 메서드 */
//    public void addStock(int stockQuantity) {
//        this.stockQuantity += stockQuantity;
//    }
//    public void removeStock(int stockQuantity) {
//        if (this.stockQuantity < stockQuantity) {
//            throw new NotEnoughStockException(NOT_ENOUGH_STOCK.toString());
//        }
//        this.stockQuantity -= stockQuantity;
//    }

}
