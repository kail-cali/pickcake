package co.pickcake.reservedomain.searchcake.dto;


import co.pickcake.reservedomain.entity.item.Cake;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.*;

/* 모든 계층에서 포괄적으로 사용하는 Dto 이기 때문에 수정 XX */
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CakeSimpleSearch {
    @JsonProperty("itemId")
    private Long itemId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("brand")
    private String brand;
    @JsonProperty("profile")
    private CakeProfileImageDto profile;
    @JsonProperty("price")
    private Integer price;

    public CakeSimpleSearch(Cake c) {
        itemId = c.getId();
        name = c.getName();
        brand = c.getBrand();
        price = c.getPrice();
        profile = new CakeProfileImageDto(c.getCakeImages());
    }
}
