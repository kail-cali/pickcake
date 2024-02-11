package co.pickcake.reservedomain.searchcake.service;

import co.pickcake.aop.util.exception.NoDataException;
import co.pickcake.reservedomain.entity.item.Cake;
import co.pickcake.reservedomain.searchcake.dto.CakeCategorySearch;

import co.pickcake.reservedomain.searchcake.dto.CakeDetailSearch;
import co.pickcake.reservedomain.searchcake.dto.CakeSimpleSearch;
import co.pickcake.reservedomain.searchcake.repository.CakeUserRepository;
import co.pickcake.shopdomain.entity.Shop;
import co.pickcake.testconfig.TestDataItem;
import co.pickcake.testconfig.TestDataSize;

import co.pickcake.util.TestInitDB;


import jakarta.persistence.TransactionRequiredException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.transaction.annotation.Transactional;


import java.util.List;


import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class CakeSearchServiceTest {

    @Autowired
    private CakeSearchService cakeSearchService;

    @Autowired
    private CakeUserRepository cakeUserRepository;

    @Autowired
    public TestInitDB testInitDB;  // 메서드 단위 트랜잭션 관리가 필요합니다. 제약사항 참고


    @AfterEach
    void tearDown() {

    }

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
        List<Cake> all = cakeUserRepository.findAll(0, 10);
        //then
        assertThat(all.size()).isEqualTo(0);
    }
    @Test
    @DisplayName("테스트 클래스 정합성 검증[success]: 테스트 클래스와 유닛테스트 정합성 테스트, 반드시 데이터가 한번 넣은 만큼 존재해야한다.")
    @Transactional
    void findAllSuccessOne() {
        //given
        TestDataSize testDataSize = testInitDB.dbInitWithItems();
        //when
        List<Cake> all = cakeUserRepository.findAll(0, 10);
        //then
        assertThat(all.size()).isEqualTo(testDataSize.getSize());
    }

    @Test
    @DisplayName("테스트 클래스 정합성 검증[success]: 테스트 클래스와 유닛테스트 정합성 테스트, 반드시 데이터가 한번 넣은 만큼 존재해야한다. 두번째 검증")
    @Transactional
    void findAllSuccessTwo() {
        //given
        TestDataSize testDataSize = testInitDB.dbInitWithItems();
        //when
        List<Cake> all = cakeUserRepository.findAll(0, 10);
        //then
        assertThat(all.size()).isEqualTo(testDataSize.getSize());
    }

    @Test
    @DisplayName("테스트 클래스 정합성 검증[fail]: 유닛 테스트 상 마지막으로 검증 시 데이터가 조회되지 않아야 한다.")
    @Transactional
    void findAllFailLast() {
        //given
        //when
        List<Cake> all = cakeUserRepository.findAll(0, 10);
        //then
        assertThat(all.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("데이터 검증[success]: 브랜드 이름으로 조회 시 상품이 제대로 출력되어야 한다.")
    @Transactional
    void findByBrandSuccess() {
        //given
        TestDataSize testDataSize = testInitDB.dbInitWithItems();
        //when
        List<CakeSimpleSearch> byBrand = cakeSearchService.findByBrand(0, 10, "신라호텔");
        //then
        // 아이템 숫자가 일치해야 하며
        assertThat(byBrand.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("데이터 검증[fail]: 없는 브랜드 이름으로 조회 시 테스트")
    @Transactional
    void findByBrandFail() {
        //given
        TestDataSize testDataSize = testInitDB.dbInitWithItems();
        //when
        List<CakeSimpleSearch> byBrand = cakeSearchService.findByBrand(0, 10, "없는 브랜드");
        //then
        // 아이템 숫자가 일치해야 하며
        assertThat(byBrand.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("데이터 검증[success]: 카테고리 별 조회 되는지 테스트")
    @Transactional
    void findBySingleCategory() {

        //given
        TestDataSize testDataSize = testInitDB.dbInitWithItems();
        //when
        List<CakeCategorySearch> bySingleCategory = cakeSearchService.findBySingleCategory(0, 10, "23 시즌 크리스마스");
        //then
        // 아이템 숫자가 일치해야 하며
        assertThat(bySingleCategory.size()).isEqualTo(testDataSize.getSize());
        // 데이터 null 체크 검정
        bySingleCategory.stream()
                .map(o -> assertThat(o.getBrand()).isNotEmpty())
                .collect(Collectors.toList());
    }

    @Test
    @DisplayName("데이터 검증[fail]: 존재하지 않는 카테고리로 조회 시 결과가 나오지 않는 지 테스트")
    @Transactional
    void findBySingleCategoryFail() {
        //given
        TestDataSize testDataSize = testInitDB.dbInitWithItems();
        String noExistsCategory = "없는 카테고라";
        //when
        List<CakeCategorySearch> bySingleCategory = cakeSearchService.findBySingleCategory(0, 10, noExistsCategory);
        //then
        // 아이템 숫자가 일치해야 하며
        assertThat(bySingleCategory.size()).isEqualTo(0);

//        bySingleCategory.stream()
//                .map(o -> assertThat(o.getBrand()).isEmpty())
//                .collect(Collectors.toList());

    }

    @Test
    void findByNameOnLike() {
    }

    @Test
    void findBySingleCategorySim() {
    }

    @Test
    @DisplayName("데이터 검증[success]: 단건 조회, 존재하는 아이디로 조회 시 데이터를 가져오는 지 테스트")
    @Transactional
    void findByIdSuccess() {
        //given
        TestDataItem testDataItem = testInitDB.dbInitWithSingleItem();
        //when
        CakeSimpleSearch byId = cakeSearchService.findById(testDataItem.getItemId());
        Cake orig = (Cake) testDataItem.getItems().get("cake1");

        //then
        assertThat(byId.getBrand()).isEqualTo(orig.getBrand());
        assertThat(byId.getName()).isEqualTo(orig.getName());
        assertThat(byId.getPrice()).isEqualTo(orig.getPrice());
    }
    @Test
    @DisplayName("데이터 검증[fail]: 단건 조회, 존재하지 않는 아이디로 조회 시 커스텀 에러 발생하는 지 테스트")
    @Transactional
    void findByIdFail()  {
        //given
        Long noExistId = 10101L;
        TestDataItem testDataItem = testInitDB.dbInitWithSingleItem();
        //when
        //then
        assertThatThrownBy(() -> cakeSearchService.findById(noExistId)).isInstanceOf(NoDataException.class);

    }

    @Test
    @DisplayName("데이터 검증[success]: 단건 아이템 상세 조회 api 테스트 ")
    @Transactional
    public void findBySingleDetail() {
        //given
        TestDataItem testDataItem = testInitDB.dbInitWithSingleItem();
        Cake cake1 = (Cake) testDataItem.getItems().get("cake1");
        Shop shop1 = (Shop) testDataItem.getItems().get("shop1");
        //when
        System.out.println("cake1 = " + cake1.getId());
        System.out.println("testDataItem = " + testDataItem.getItemId());
        List<Cake> all = cakeUserRepository.findAll(0, 10);
        List<Cake> collect = all.stream().collect(Collectors.toList());
        for (Cake auto : collect) {
            System.out.println("auto.getId() = " + auto.getId());
        }
        CakeDetailSearch bySingleDetail = cakeSearchService.findBySingleDetail(cake1.getId());
        //then

        assertThat(bySingleDetail.getBrand()).isEqualTo(cake1.getBrand());
        assertThat(bySingleDetail.getShop().getShopId()).isEqualTo(cake1.getShop().getId());
        assertThat(bySingleDetail.getShop().getReserveInfoItem().getNeedReservation()).isEqualTo(cake1.getShop().getReserveInfo().getNeedReservation());
    }

    @Test
    @DisplayName("데이터 검증[fail]: 단건 아이템 상세 조회 api 테스트 ")
    @Transactional
    public void findBySingleDetailFail() {
        //given
        TestDataItem testDataItem = testInitDB.dbInitWithSingleItem();
        Cake cake1 = (Cake) testDataItem.getItems().get("cake1");
        Shop shop1 = (Shop) testDataItem.getItems().get("shop1");
        Long noExistItem = 10101L;
        //when
        //then
        assertThatThrownBy(() -> cakeSearchService.findBySingleDetail(noExistItem)).isInstanceOf(EmptyResultDataAccessException.class);
    }


}