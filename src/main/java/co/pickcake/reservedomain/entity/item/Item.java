package co.pickcake.reservedomain.entity.item;

import co.pickcake.recommend.entity.Heart;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/* TODO  추상클래스에 AuditingEntityListener 붙이는 것에 대해 다시 검토 필요 */
@Entity @Getter @EntityListeners(AuditingEntityListener.class)
public abstract class Item {

    @Id @GeneratedValue
    @Column(name="item_id")
    private Long id;
    private String name;
    private int price;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    /* 수정 메서드 */
    public void setName(String name) {this.name = name;}
    public void setPrice(int price) {this.price = price;}

    public void setId(Long id) {this.id = id;}

    /* 추후 기능 추가 시 다시 추가 및 수정 예정 */
        /* api */
    @OneToMany(mappedBy = "item")
    private List<Heart> hearts = new ArrayList<>();
}
