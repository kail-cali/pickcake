package co.pickcake.reservedomain.entity.item;

import co.pickcake.common.entity.Address;
import jakarta.persistence.*;
import lombok.Getter;


@Entity @Getter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name="item_id")
    private Long id;
    private String name;
    private int price;

    /* 수정 메서드 */
    public void setName(String name) {this.name = name;}
    public void setPrice(int price) {this.price = price;}

    public void setId(Long id) {this.id = id;}

    /* 추후 기능 추가 시 다시 추가 및 수정 예정 */
    //    /* api */
//    @OneToMany(mappedBy = "item")
//    private List<Heart> hearts = new ArrayList<>();
}
