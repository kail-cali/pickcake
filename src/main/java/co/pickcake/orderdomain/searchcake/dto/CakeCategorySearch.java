package co.pickcake.orderdomain.searchcake.dto;


import co.pickcake.orderdomain.entity.item.EventCakeCategory;
import lombok.Data;

import java.util.List;

@Data
public class CakeCategorySearch {

    private Long itemId;

    private String itemName;
    private String categoryName;
    private String imagePath;
    private int price;

    private String brand;


    private List<CakeCategoryItem> categories;


    public CakeCategorySearch(Long itemId, String itemName, String imagePath, int price, String brand) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.imagePath = imagePath;
        this.price = price;
        this.brand = brand;
    }

    public CakeCategorySearch(EventCakeCategory e) {
        itemId = e.getId();
        itemName = e.getCake().getName();
        categoryName = e.getCakeCategory().getName();
        imagePath = e.getCake().getImagePath();
        price = e.getCake().getPrice();
        brand = e.getCake().getBrand();
    }

}
