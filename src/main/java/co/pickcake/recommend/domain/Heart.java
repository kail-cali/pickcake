package co.pickcake.recommend.domain;

import co.pickcake.auth.domain.Member;
import co.pickcake.reservation.domain.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
* like event relation -> TODO API for like
* */
@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "heart")
public class Heart {

    @Id @GeneratedValue
    @Column(name="heart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "item_id")
    private Item item;

}
