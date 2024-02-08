package co.pickcake.reservedomain.searchcake.dto;


import co.pickcake.reservedomain.entity.item.EventCakeCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CakeCategorySearch {

    @JsonIgnore
    private Long itemId;
    private String itemName;
    private String categoryName;
    private int price;
    private String brand;
    private CakeProfileImageDto profile;

    public CakeCategorySearch(Long itemId, String itemName,  int price, String brand) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.brand = brand;
    }

    public CakeCategorySearch(EventCakeCategory e) {
        itemId = e.getId();
        itemName = e.getCake().getName();
        categoryName = e.getCakeCategory().getName();
        price = e.getCake().getPrice();
        brand = e.getCake().getBrand();
        profile = new CakeProfileImageDto(e.getCake().getCakeImages());
    }
}
