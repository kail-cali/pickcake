package co.pickcake.reservedomain.searchcake.repository;

import co.pickcake.authdomain.entity.Member;
import co.pickcake.reservedomain.entity.item.Cake;
import co.pickcake.shopdomain.entity.Shop;
import co.pickcake.testconfig.TestDataItem;
import co.pickcake.testconfig.TestDataSize;
import co.pickcake.util.TestInitDB;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CakeRepositoryJPATest {

    @Autowired private CakeRepository cakeRepository;

    @Autowired private TestInitDB testInitDB;

    @Autowired private EntityManager em;



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

    @Test
    @DisplayName("데이터 검증")
    @Transactional
    void findByBrand() {
        //given
        TestDataItem testDataItem = testInitDB.dbInitWithSingleItem();
        Cake cake1 = (Cake) testDataItem.getItems().get("cake1");
        Member member1 = (Member) testDataItem.getItems().get("member1");
        Shop shop = (Shop) testDataItem.getItems().get("shop1");
        Long toFind = cake1.getId();
        //when
        List<Cake> byBrand = cakeRepository.findByBrand("신라호텔");
        //then
        Assertions.assertThat(byBrand.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("데이터 검증: 페이징을 적용하여 데이터가 잘 조회 되는지 테스트")
    @Transactional
    void findByBrandPaging() {
        //given
        TestDataSize testDataSize = testInitDB.dbInitWithItems();
        PageRequest pageRequest = PageRequest.of(0,2, Sort.by(Sort.Direction.DESC, "name"));
        //when
        List<Cake> byBrand = cakeRepository.findByBrand("신라호텔");
        Page<Cake> byBrandAndPaging = cakeRepository.findByBrand("신라호텔", pageRequest);
        //then

        Assertions.assertThat(byBrandAndPaging.getTotalPages()).isEqualTo(1);
        Assertions.assertThat(byBrandAndPaging.getTotalElements()).isEqualTo(2);
    }

    @Test
    @DisplayName("readonly ")
    @Transactional
    void findReadonly() {
        //given
        TestDataItem testDataItem = testInitDB.dbInitWithSingleItem();
        Cake cake1 = (Cake) testDataItem.getItems().get("cake1");
        Member member1 = (Member) testDataItem.getItems().get("member1");
        Shop shop = (Shop) testDataItem.getItems().get("shop1");
        Long toFind = cake1.getId();
        em.flush();
        em.clear();
        //when

        Cake found = cakeRepository.findReadOnlyByName("화이트 홀리데이 케이크");
        found.setName("new.cakeName");
        em.flush();
        //then
        Cake foundNext = cakeRepository.findReadOnlyByName("화이트 홀리데이 케이크");
        Cake foundChanged = cakeRepository.findReadOnlyByName("new.cakeName");
        Assertions.assertThat(foundNext).isNotNull();
        Assertions.assertThat(foundChanged).isNull();

    }

}