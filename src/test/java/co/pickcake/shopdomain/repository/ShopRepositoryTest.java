package co.pickcake.shopdomain.repository;

import co.pickcake.reservedomain.entity.ReserveInfo;
import co.pickcake.reservedomain.entity.item.Cake;
import co.pickcake.reservedomain.searchcake.repository.CakeAdminRepository;
import co.pickcake.policies.filename.policy.FileUuidGeneratePolicy;
import co.pickcake.shopdomain.entity.Shop;
import co.pickcake.testconfig.TestDataItem;
import co.pickcake.testconfig.TestDataSize;
import co.pickcake.util.TestInitDB;
import jakarta.persistence.TransactionRequiredException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;


import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ShopRepositoryTest {
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private CakeAdminRepository cakeAdminRepository;
    @Autowired
    private FileUuidGeneratePolicy fileNamePolicy;
    @Autowired
    private TestInitDB testInitDB;

    @Test
    @DisplayName("테스트 클래스 트랜잭션 검증: 커스텀 테스트 클래스는 메서드 단위 트랜잭션 관리가 필요합니다. 이 테스트를 참고하여 수정하십시오.")
    public void unitTestClassTest() {
        //given
        //when
        //then
        assertThatThrownBy(()->testInitDB.dbInitWithSingleItem()).isInstanceOf(TransactionRequiredException.class);
    }
    @Test
    @DisplayName("테스트 클래스 정합성 검증[fail]: 아무런 init 없이 전체 조회 시 0개의 데이터를 가지고 있어야 한다. ")
    void findAllFail() {
        //given
        //when
        List<Shop> all = shopRepository.findAll(0, 10);
        //then
        assertThat(all.size()).isEqualTo(0);
    }
    @Test
    @DisplayName("테스트 클래스 정합성 검증[success]: 테스트 클래스와 유닛테스트 정합성 테스트, 반드시 데이터가 한번 넣은 만큼 존재해야한다.")
    @Transactional
    void findAllSuccessOne() {
        //given
        TestDataItem testDataItem = testInitDB.dbInitWithSingleItem();
        Shop shop = (Shop) testDataItem.getItems().get("shop1");
        //when
        List<Shop> all = shopRepository.findAll(0, 10);
        //then
        assertThat(all.size()).isEqualTo(1);
    }
    @Test
    @DisplayName("테스트 클래스 정합성 검증[success]: 테스트 클래스와 유닛테스트 정합성 테스트, 반드시 데이터가 한번 넣은 만큼 존재해야한다. 두번째 검증")
    @Transactional
    void findAllSuccessTwo() {
        //given
        TestDataSize testDataSize = testInitDB.dbInitWithItems();
        //when
        List<Shop> all = shopRepository.findAll(0, 10);
        //then
        assertThat(all.size()).isEqualTo(2);
    }
    @Test
    @DisplayName("테스트 클래스 정합성 검증[fail]: 유닛 테스트 상 마지막으로 검증 시 데이터가 조회되지 않아야 한다.")
    @Transactional
    void findAllFailLast() {
        //given
        //when
        List<Shop> all = shopRepository.findAll(0, 10);
        //then
        assertThat(all.size()).isEqualTo(0);
    }
    @Test
    @DisplayName("데이터 검증[success]: shop 정보가 db에 잘 넣어지는 지 테스트")
    @Transactional
    void findById() {
        //given
        TestDataItem testDataItem = testInitDB.dbInitWithSingleItem();
        //when
        Shop shop = (Shop) testDataItem.getItems().get("shop1");
        Shop findFromDao = shopRepository.findById(shop.getId());
        //then
        Assertions.assertThat(shop).isEqualTo(findFromDao);
        assertThat(shop.getShopName()).isEqualTo(findFromDao.getShopName());
        assertThat(shop.getReserveInfo()).isEqualTo(findFromDao.getReserveInfo());
    }
    @Test
    @DisplayName("데이터 검증[success]: 이름으로 검색 시 아이템을 찾을 수 있는 지 테스트")
    @Transactional
    void findByName() {
        // given
        TestDataItem testDataItem = testInitDB.dbInitWithSingleItem();
        //when
        Shop shop = (Shop) testDataItem.getItems().get("shop1");
        List<Shop> byName = shopRepository.findByName(shop.getShopName());
        //then
        Assertions.assertThat(shop).isEqualTo(byName.getFirst());
        assertThat(shop.getShopName()).isEqualTo(byName.getFirst().getShopName());
        assertThat(shop.getReserveInfo()).isEqualTo(byName.getFirst().getReserveInfo());
    }
    @Test
    @DisplayName("데이터 검증[success]: 가게 정보에 상품을 추가할 때 db에서 잘 저장되는 지 테스트")
    @Transactional
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
    @DisplayName("데이터 검증[success]: 가게 정보에 다중 상품을 추가할 때 db에서 잘 저장되는 지 테스트")
    @Transactional
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
    @DisplayName("데이터 검증[success]: 가게 정보에 상품에 대한 이미지까지 붙였을 때 잘 조회되는 지 테스트 ")
    @Transactional
    public void addCakeImageFileWithShopTest() {
        TestDataSize testDataSize = testInitDB.dbInitWithItems();
        //when
        List<Shop> all = shopRepository.findAll(0, 10);
        //then
        Assertions.assertThat(all.size()).isEqualTo(2);

    }
    @Test
    @DisplayName("비즈니스 로직 검증[success]: 상품 예약 관련, 현장 판매일 경우 다른 옵션들이 디폴트값으로 세팅되어 있는지 테스트")
    @Transactional
    void findByIdWithReserveInfo1() {
        //given
        Shop shop = Shop.createShop("신라호텔", "https://www.shillahotels.com/membership/inquires/aboutShilla/memSeoulHotel.do",
                "02-301-1111", "seoul", "중구", "3000",
                true, true, 100,
                LocalTime.of(10, 0), LocalTime.of(16, 0),
                "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EC%8B%A0%EB%9D%BC%ED%98%B8%ED%85%94", "https://www.instagram.com/shillahotels/"
        );
        shopRepository.save(shop);
        //when
        Shop findFromDao = shopRepository.findById(shop.getId());
        ReserveInfo reserveInfo = shop.getReserveInfo();
        ReserveInfo findFromDaoReserveInfo = findFromDao.getReserveInfo();
        //then
        Assertions.assertThat(reserveInfo).isEqualTo(findFromDaoReserveInfo);
        assertThat(reserveInfo.getOnSiteSaleOnly()).isEqualTo(true);
        assertThat(reserveInfo.getNeedReservation()).isEqualTo(false);
        assertThat(reserveInfo.getNeedReservationBeforeDay()).isEqualTo(0);
    }
    @Test
    @DisplayName("비즈니스 로직 검증[success]: 상품 예약 관련, 현장 판매가 아닐 경우,  다른 옵션들이 주어진 값 그대로 세팅되어 있는지 테스트")
    @Transactional
    void findByIdWithReserveInfo2() {
        //given
        Shop shop = Shop.createShop("신라호텔", "https://www.shillahotels.com/membership/inquires/aboutShilla/memSeoulHotel.do",
                "02-301-1111", "seoul", "중구", "3000",
                false, true, 100,
                LocalTime.of(10, 0), LocalTime.of(16, 0),
                "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EC%8B%A0%EB%9D%BC%ED%98%B8%ED%85%94", "https://www.instagram.com/shillahotels/"
        );
        shopRepository.save(shop);
        //when
        Shop findFromDao = shopRepository.findById(shop.getId());
        ReserveInfo reserveInfo = shop.getReserveInfo();
        ReserveInfo findFromDaoReserveInfo = findFromDao.getReserveInfo();
        //then
        Assertions.assertThat(reserveInfo).isEqualTo(findFromDaoReserveInfo);
        assertThat(reserveInfo.getOnSiteSaleOnly()).isEqualTo(false);
        assertThat(reserveInfo.getNeedReservation()).isEqualTo(true);
        assertThat(reserveInfo.getNeedReservationBeforeDay()).isEqualTo(100);
    }
    @Test
    @DisplayName("비즈니스 로직 검증[success]: 상품 예약 시, 현장 판매도 아니고 예약도 필요하지 않으면 사전 예약 일자가 0이어야 한다")
    @Transactional
    void findByIdWithReserveInfo3() {
        //given
        Shop shop = Shop.createShop("신라호텔", "https://www.shillahotels.com/membership/inquires/aboutShilla/memSeoulHotel.do",
                "02-301-1111", "seoul", "중구", "3000",
                false, false, 100,
                LocalTime.of(10, 0), LocalTime.of(16, 0),
                "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EC%8B%A0%EB%9D%BC%ED%98%B8%ED%85%94", "https://www.instagram.com/shillahotels/"
        );
        shopRepository.save(shop);
        //when
        Shop findFromDao = shopRepository.findById(shop.getId());
        ReserveInfo reserveInfo = shop.getReserveInfo();
        ReserveInfo findFromDaoReserveInfo = findFromDao.getReserveInfo();
        //then
        Assertions.assertThat(reserveInfo).isEqualTo(findFromDaoReserveInfo);
        assertThat(reserveInfo.getOnSiteSaleOnly()).isEqualTo(false);
        assertThat(reserveInfo.getNeedReservation()).isEqualTo(false);
        assertThat(reserveInfo.getNeedReservationBeforeDay()).isEqualTo(0);
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