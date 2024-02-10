package co.pickcake.reservedomain.searchcake.dto;


import co.pickcake.reservedomain.entity.item.EventCakeCategory;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
public class CakeDetailSearch {

    private String name;
    private String brand;
    private String imagePath;
    private Integer price;
    private Integer lowerPrice;
    private Integer upperPrice;
    private List<CakeCategoryItem> cakeCategories;

}
