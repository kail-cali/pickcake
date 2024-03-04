package co.pickcake.reservedomain.searchcake.dto;


import co.pickcake.reservedomain.entity.item.Cake;
import lombok.*;

/* 모든 계층에서 포괄적으로 사용하는 Dto 이기 때문에 수정 XX */
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CakeSimpleSearch {

    private Long itemId;
    private String name;
    private String brand; // -> shop info
    private CakeProfileImageDto profile;
    private Integer price;

    public CakeSimpleSearch(Cake c) {
        itemId = c.getId();
        name = c.getName();
        brand = c.getBrand();
        price = c.getPrice();
        profile = new CakeProfileImageDto(c.getCakeImages());
    }
}
