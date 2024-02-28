package co.pickcake.reservedomain.searchcake.response;

import co.pickcake.reservedomain.entity.item.Cake;
import co.pickcake.reservedomain.searchcake.dto.CakeProfileImageDto;
import co.pickcake.shopdomain.dto.ShopSearch;
import lombok.Data;

@Data
public class CakeDetailSearchWithFeature {

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
