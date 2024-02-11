package co.pickcake.reservedomain.searchcake.dto;

import co.pickcake.reservedomain.entity.item.Cake;
import co.pickcake.shopdomain.dto.ShopSearch;
import lombok.Getter;
import lombok.Setter;

/* XXX Detail 조회 Dto 는 서비스 정책, 기능 추가에 따라 상시 변화할 수 있어 컨트롤러까지만 사용할 것
*  해당 Dto 는 상품 정보 + 가게 정보 + 가게의 예약에 관한 정보 등
*  여러 개의 데이터를 한번에 조인해서 조립하거나 각자 가져와서 조립할 수 있는 데 현 버전의 정책 상 쿼리의 갯수를 줄이기로 함 */

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
