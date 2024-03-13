package co.pickcake.reservedomain.searchcake.response;

import co.pickcake.reservedomain.entity.item.Cake;
import co.pickcake.reservedomain.searchcake.dto.CakeProfileImageDto;
import co.pickcake.shopdomain.dto.ShopSearch;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CakeDetailSearchWithFeature {

    /* TODO : front 에서 사용하는 네이밍 룰 맞추어서 DTO JsonProperty 일괄 수정 필요 */
    @JsonProperty("itemId")
    private Long itemId;
    private String name;
    private String brand;
    private Integer price;
    private CakeProfileImageDto profile;

    /* detail 조회 시 추가 조회 내용 */
    private ShopSearch shop;
    public CakeDetailSearchWithFeature(Cake c) {
        itemId = c.getId();
        name = c.getName();
        brand = c.getBrand();
        price = c.getPrice();
        profile = new CakeProfileImageDto(c.getCakeImages());
        shop =  new ShopSearch(c.getShop());
    }
}
