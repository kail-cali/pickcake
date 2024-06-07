package co.pickcake.reservation.domain.item;


import co.pickcake.aop.datetime.AuditOnTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventCakeCategory extends AuditOnTime {

    @Id
    @GeneratedValue
    @Column(name = "event_cake_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Cake cake;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CakeCategory cakeCategory;

    private LocalDateTime dateTime;
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setCakeCategory(CakeCategory cakeCategory) {
        this.cakeCategory = cakeCategory;
    }

    public void setCake(Cake cake) {
        this.cake = cake;
    }

    public static EventCakeCategory addCakeCategory(Cake cake, CakeCategory cakeCategory) {
        EventCakeCategory eventCakeCategory = new EventCakeCategory();
        eventCakeCategory.setDateTime(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        cakeCategory.addCakeList(eventCakeCategory); // -> Mapping 테이블 참조해야함
        cake.addCakeCategory(eventCakeCategory);    // -> Mapping 테이블 참조해야함
        return eventCakeCategory;
    }

    public static List<EventCakeCategory> addCakeCategories(Cake cake, CakeCategory... cakeCategories) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        List<EventCakeCategory> eventCakeCategories = new ArrayList<>();

        for (CakeCategory cakeCategory : cakeCategories) {
            EventCakeCategory eventCakeCategory = new EventCakeCategory();
            eventCakeCategory.setDateTime(now);
            cakeCategory.addCakeList(eventCakeCategory);
            cake.addCakeCategory(eventCakeCategory);
            eventCakeCategories.add(eventCakeCategory);
        }
        return eventCakeCategories;
    }

}
