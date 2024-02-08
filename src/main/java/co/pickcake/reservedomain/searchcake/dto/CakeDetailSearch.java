package co.pickcake.reservedomain.searchcake.dto;


import co.pickcake.reservedomain.entity.item.EventCakeCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
public class CakeDetailSearch {

    private String name;
    private String brand; // -> shop info
    private String imagePath;
    private int price;
    private int lowerPrice;
    private int upperPrice;
    private List<CakeCategoryItem> cakeCategories; //  PS -> OrderItemQueryDto

}
