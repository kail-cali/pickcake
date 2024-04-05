package co.pickcake.shopdomain.repository;

import co.pickcake.shopdomain.entity.Shop;
import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootTest
public class ShopJpaRepositoryTest {


    @Autowired ShopJpaRepository shopRepository;


    @Test
    public void testFindByDetail() {

        //given
        LocalDateTime now = LocalDateTime.now();
        Shop shop1 = Shop.createShop("신라 호텔", "https://www.shillahotels.com/membership/inquires/aboutShilla/memSeoulHotel.do",
                "02-301-1111", "seoul", "중구", "3000",
                false, true, 2,
                LocalTime.of(10, 0), LocalTime.of(16, 0),
                "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EC%8B%A0%EB%9D%BC%ED%98%B8%ED%85%94", "https://www.instagram.com/shillahotels/"
        );
        shopRepository.save(shop1);
        // when
        Shop byIdWithDetails = shopRepository.findByIdWithDetails(shop1.getId());

        // then
        Assertions.assertThat(byIdWithDetails).isNotNull();
        Assertions.assertThat(byIdWithDetails.getId()).isEqualTo(shop1.getId());
        Assertions.assertThat(byIdWithDetails.getCreateAt()).isAfter(now);

    }






}