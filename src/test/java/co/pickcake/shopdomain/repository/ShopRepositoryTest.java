package co.pickcake.shopdomain.repository;

import co.pickcake.reservedomain.entity.item.Cake;
import co.pickcake.reservedomain.searchcake.repository.CakeAdminRepository;
import co.pickcake.policies.filename.policy.FileUuidGeneratePolicy;
import co.pickcake.shopdomain.entity.Shop;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@SpringBootTest
class ShopRepositoryTest {
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private CakeAdminRepository cakeAdminRepository;
    @Autowired
    private FileUuidGeneratePolicy fileNamePolicy;

    @Test
    @DisplayName("데이터 검정: shop 정보가 db에 잘 넣어지는 지 테스트")
    void findById() {
        Shop shop = Shop.createShop("신라 호텔", "http://shinrahotel.com", "02-302-1111","seoul", "중구", "01123");
        shopRepository.save(shop);
        Assertions.assertThat(shop).isEqualTo(shopRepository.findById(shop.getId()));
    }

    @Test
    @DisplayName("데이터 검정: 이름으로 검색 시 아이템을 찾을 수 있는 지 테스트")
    void findByName() {
        // given
        Shop shop = Shop.createShop("신라 호텔", "http://shinrahotel.com", "02-302-1111","seoul", "중구", "01123");
        shopRepository.save(shop);

        // when
        List<Shop> items = shopRepository.findByName("신라 호텔");

        // then

        Assertions.assertThat(items.getFirst()).isEqualTo(shop);
    }

    @Test
    @DisplayName("데이터 검정: 가게 정보에 상품을 추가할 때 db에서 잘 저장되는 지 테스트")
    public void addCakeItemTest() {
        //given
        Shop shop = Shop.createShop("신라 호텔", "http://shinrahotel.com", "02-302-1111","seoul", "중구", "01123");
        shopRepository.save(shop);


        Cake cake1 = Cake.createCakeWithImage("초코 케이크", "신라 호텔", "우유 생크림이 들어간 케이크",
                150000, fileNamePolicy);
        cakeAdminRepository.save(cake1);

        //when
        shop.addCake(cake1);

        //then
        // 하나 추가했을 때 결과가 같아야 하며
        Assertions.assertThat(shop.getCakeList().size()).isEqualTo(1);
        // 첫번쨰 아이템의 클래스 해시가 같아야 하며
        Assertions.assertThat(shop.getCakeList().getFirst()).isEqualTo(cake1);
        // 다시 디비를 통회 조회해서 가져온 아이템도 같아야 한다
        Assertions.assertThat(shop.getCakeList().getFirst()).isEqualTo(cakeAdminRepository.findById(cake1.getId()));

        System.out.println("cake1.getCakeImages().getImageName() = " + cake1.getCakeImages().getImageName());
        Assertions.assertThat(cake1.getCakeImages().getImageName()).isNotEmpty();
    }

    @Test
    @DisplayName("데이터 검정: 가게 정보에 다중 상품을 추가할 때 db에서 잘 저장되는 지 테스트")
    public void addCakeItemsTest() {
        //given
        Shop shop = Shop.createShop("신라 호텔", "http://shinrahotel.com", "02-302-1111","seoul", "중구", "01123");



        Cake cake1 = Cake.createCakeWithImage("초코 케이크", "신라 호텔", "우유 생크림이 들어간 케이크",
                150000, fileNamePolicy);
//        cakeAdminRepository.save(cake1);
        Cake cake2 = Cake.createCakeWithImage("초코 케이크2", "신라 호텔", "우유 생크림이 들어간 케이크",
                150000, fileNamePolicy);
//        cakeAdminRepository.save(cake2);

        //when
        shop.addCake(cake1);
        shop.addCake(cake2);
        shopRepository.save(shop); // -> CASACADE 전파 ALL 인 경우 해당 테스트를 사용할 수 있도록 표시 필요
        //then
        // 하나 추가했을 때 결과가 같아야 하며
        Assertions.assertThat(shop.getCakeList().size()).isEqualTo(2);
        // 첫번쨰 아이템의 클래스 해시가 같아야 하며
        Assertions.assertThat(shop.getCakeList().getFirst()).isEqualTo(cake1);
        // 다시 디비를 통회 조회해서 가져온 아이템도 같아야 한다
        Assertions.assertThat(shop.getCakeList().getFirst()).isEqualTo(cakeAdminRepository.findById(cake1.getId()));
    }


    @Test
    @DisplayName("데이터 검증: 가게 정보에 상품에 대한 이미지까지 붙였을 때 잘 조회되는 지 테스트 ")
    public void addCakeImageFileWithShopTest() {

    }


    @Test
    @DisplayName("데이터 검증: 가게 정보에 상품들의 리스트를 조회 시 잘 조회되는 지 테스트 -> 서비스 api 단으로 넘길 예정")
    public void searchShopCakeListTest() {

    }


    /* 부하 테스트 */
    @Test
    @DisplayName("부하 테스트: 가게 통합 정보를 여러개 한번에 저장 시 부하 테스트")
    public void saveHardTest(){

    }

    @Test
    @DisplayName("부하 테스트: 가게 통합 정보를 한번에 여러번 조회 시 부하 테스트")
    public void searchFullInfoTest(){

    }

}