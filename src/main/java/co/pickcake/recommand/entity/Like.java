package co.pickcake.recommand.entity;

import co.pickcake.authdomain.entity.Member;
import co.pickcake.orderdomain.entity.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
* like event relation -> TODO API for like
* */
@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {

    @Id @GeneratedValue
    @Column(name="like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "item_id")
    private Item item;

}
