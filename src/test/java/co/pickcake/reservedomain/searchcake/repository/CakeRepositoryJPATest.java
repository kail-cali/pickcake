package co.pickcake.reservedomain.searchcake.repository;

import co.pickcake.authdomain.entity.Member;
import co.pickcake.reservedomain.entity.item.Cake;
import co.pickcake.shopdomain.entity.Shop;
import co.pickcake.testconfig.TestDataItem;
import co.pickcake.util.TestInitDB;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CakeRepositoryJPATest {


    @Autowired
    private CakeRepository cakeRepository;

    @Autowired
    private TestInitDB testInitDB;

    @Test
    @DisplayName("데이터 검증[success] 아이템 ID 상세페이지  데이터 조회했을 때 결과  ")
    @Transactional
    void findByIdDetails() {
        //given
        TestDataItem testDataItem = testInitDB.dbInitWithSingleItem();
        Cake cake1 = (Cake) testDataItem.getItems().get("cake1");
        Member member1 = (Member) testDataItem.getItems().get("member1");
        Shop shop = (Shop) testDataItem.getItems().get("shop1");
        Long toFind = cake1.getId();

        //when
        Cake byIdDetails = cakeRepository.findByIdDetails(toFind);

        //then
        Assertions.assertThat(byIdDetails).isNotNull();
        Assertions.assertThat(byIdDetails.getShop()).isEqualTo(shop);

    }
}