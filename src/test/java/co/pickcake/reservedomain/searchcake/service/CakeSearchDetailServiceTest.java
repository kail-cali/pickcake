package co.pickcake.reservedomain.searchcake.service;

import co.pickcake.reservedomain.entity.item.Cake;
import co.pickcake.reservedomain.searchcake.response.CakeDetailSearchWithFeature;
import co.pickcake.shopdomain.entity.Shop;
import co.pickcake.testconfig.TestDataItem;
import co.pickcake.util.TestInitDB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CakeSearchDetailServiceTest {

    @Autowired
    private CakeSearchDetailService cakeSearchDetailService;
    @Autowired private TestInitDB testInitDB;

    @Test
    @DisplayName("연동 api 기능 테스트[success]")
    @Transactional
    void findBySingleDetailWithMap() {
        //given
        TestDataItem testDataItem = testInitDB.dbInitWithSingleItem();
        Shop shop1 = (Shop) testDataItem.getItems().get("shop1");
        Cake cake1 = (Cake) testDataItem.getItems().get("cake1");
//        //when
        CakeDetailSearchWithFeature response = cakeSearchDetailService.findBySingleDetailWithMap(cake1.getId());
//        //then
        System.out.println("response = " + response);
        org.assertj.core.api.Assertions.assertThat(response.getName()).isEqualTo(shop1.getShopName());
    }

}