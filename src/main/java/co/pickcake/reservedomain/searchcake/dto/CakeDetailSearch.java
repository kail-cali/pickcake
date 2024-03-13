package co.pickcake.reservedomain.searchcake.dto;

import co.pickcake.reservedomain.entity.item.Cake;
import co.pickcake.shopdomain.dto.ShopSearch;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CakeDetailSearch {

    /* CakeSimpleSearch 에서 가져온 조회 내용 */
    private Long itemId;
    private String name;
    private String brand; // -> item 테이블에 저장된 shopName
    private Integer price;
    private CakeProfileImageDto profile;

    /* detail 조회 시 추가 조회 내용 */
    private ShopSearch shop;
    public CakeDetailSearch(Cake c) {
        itemId = c.getId();
        name = c.getName();
        brand = c.getBrand();
        price = c.getPrice();
        profile = new CakeProfileImageDto(c.getCakeImages());
        shop =  new ShopSearch(c.getShop());
    }
    /* 기능 스펙 상 현재 빼놓은 데이터들 */
//    private List<CakeCategoryItem> cakeCategories; -> CakeDetailCategorySearch 로 분리 예정

}
