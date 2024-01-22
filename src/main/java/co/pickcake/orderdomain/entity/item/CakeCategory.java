package co.pickcake.orderdomain.entity.item;

import co.pickcake.common.entity.Category;
import co.pickcake.common.entity.Colors;
import co.pickcake.common.entity.ItemDate;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="CakeCategory")
public class CakeCategory extends Category {




    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CakeCategory parent;

    @OneToMany(mappedBy = "parent")
    private List<CakeCategory> child = new ArrayList<>();

    public void addChildCategory(CakeCategory ch) {
        this.child.add(ch);
        ch.setParent(this);
    }

    public void setParent(CakeCategory parent) {
        this.parent = parent;
    }

    public void setChild(List<CakeCategory> child) {
        this.child = child;
    }
}
