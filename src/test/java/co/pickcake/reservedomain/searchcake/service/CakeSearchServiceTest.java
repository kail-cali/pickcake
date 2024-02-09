package co.pickcake.reservedomain.searchcake.service;

import co.pickcake.aop.util.exception.NoDataException;
import co.pickcake.reservedomain.entity.item.Cake;
import co.pickcake.reservedomain.searchcake.dto.CakeCategorySearch;

import co.pickcake.reservedomain.searchcake.dto.CakeSimpleSearch;
import co.pickcake.testconfig.TestDataItem;
import co.pickcake.testconfig.TestDataSize;

import co.pickcake.util.TestInitDB;

import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;


import java.util.List;


import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class CakeSearchServiceTest {

    @Autowired
    private CakeSearchService cakeSearchService;

    @Autowired
    public TestInitDB testInitDB;

    @Test
    void findAll() {
    }

    @Test
    @DisplayName("데이터 검증[success]: 브랜드 이름으로 조회 시 상품이 제대로 출력되어야 한다.")
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
    void findByIdSuccess() {
        //given
        TestDataItem testDataItem = testInitDB.dbInitWithSingleItem();
        //when
        CakeSimpleSearch byId = cakeSearchService.findById(testDataItem.getItemId());
        Cake orig = (Cake) testDataItem.getSingleItem();

        //then
        assertThat(byId.getBrand()).isEqualTo(orig.getBrand());
        assertThat(byId.getName()).isEqualTo(orig.getName());
        assertThat(byId.getPrice()).isEqualTo(orig.getPrice());
    }
    @Test
    @DisplayName("데이터 검증[fail]: 단건 조회, 존재하지 않는 아이디로 조회 시 커스텀 에러 발생하는 지 테스트")
    void findByIdFail()  {
        //given
        Long noExistId = 10101L;
        TestDataItem testDataItem = testInitDB.dbInitWithSingleItem();
        //when
        AbstractThrowableAssert<?, ? extends Throwable> result = assertThatThrownBy(() -> cakeSearchService.findById(noExistId));
        //then
        result.isInstanceOf(NoDataException.class);

    }


}