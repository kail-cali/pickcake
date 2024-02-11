package co.pickcake.reservedomain.searchcake.dto;


import co.pickcake.reservedomain.entity.item.EventCakeCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/* 카테고리 조회용 Dto, 컨트롤러와 서비스 계층에서 사용하고 있음 */
@Data
public class CakeCategorySearch {

    @JsonIgnore
    private Long itemId;
    private String itemName;
    @NotBlank
    private String categoryName;
    private Integer price;
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
