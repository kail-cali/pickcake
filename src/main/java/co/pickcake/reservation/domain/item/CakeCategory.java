package co.pickcake.reservation.domain.item;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
public class CakeCategory {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "cakeCategory", cascade =  CascadeType.ALL)
    private List<EventCakeCategory> cakeList = new ArrayList<>();

    public void addCakeList(EventCakeCategory cakeCategory) {
        this.cakeList.add(cakeCategory);
        cakeCategory.setCakeCategory(this);
    }
    /* 생성 편의 메서드 */
    public static CakeCategory createCategory(String categoryName) {
        CakeCategory cakeCategory = new CakeCategory();
        cakeCategory.setName(categoryName);
        return cakeCategory;
    }
    public void setName(String name) {
        this.name = name;
    }
}
