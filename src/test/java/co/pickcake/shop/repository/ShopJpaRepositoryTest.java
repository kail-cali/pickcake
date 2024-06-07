package co.pickcake.shop.repository;

import co.pickcake.shop.domain.Shop;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootTest
public class ShopJpaRepositoryTest {


    @Autowired ShopJpaRepository shopRepository;



    @Test
    @DisplayName("[데이터 검증] update 연산 ")
    void testChangeShopName() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Shop shop1 = Shop.createShop("신라 호텔", "https://www.shillahotels.com/membership/inquires/aboutShilla/memSeoulHotel.do",
                "02-301-1111", "seoul", "중구", "3000",
                false, true, 2,
                LocalTime.of(10, 0), LocalTime.of(16, 0),
                "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EC%8B%A0%EB%9D%BC%ED%98%B8%ED%85%94", "https://www.instagram.com/shillahotels/"
        );
        shopRepository.save(shop1);




    }

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

    @Test
    public void testFindAllWithPAge() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Shop shop1 = Shop.createShop("신라 호텔", "https://www.shillahotels.com/membership/inquires/aboutShilla/memSeoulHotel.do",
                "02-301-1111", "seoul", "중구", "3000",
                false, true, 2,
                LocalTime.of(10, 0), LocalTime.of(16, 0),
                "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EC%8B%A0%EB%9D%BC%ED%98%B8%ED%85%94", "https://www.instagram.com/shillahotels/"
        );

        Shop shop2 = Shop.createShop("신라 호텔", "https://www.shillahotels.com/membership/inquires/aboutShilla/memSeoulHotel.do",
                "02-301-1111", "seoul", "중구", "3000",
                false, true, 2,
                LocalTime.of(10, 0), LocalTime.of(16, 0),
                "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EC%8B%A0%EB%9D%BC%ED%98%B8%ED%85%94", "https://www.instagram.com/shillahotels/"
        );

        Shop shop3 = Shop.createShop("신라 호텔", "https://www.shillahotels.com/membership/inquires/aboutShilla/memSeoulHotel.do",
                "02-301-1111", "seoul", "중구", "3000",
                false, true, 2,
                LocalTime.of(10, 0), LocalTime.of(16, 0),
                "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EC%8B%A0%EB%9D%BC%ED%98%B8%ED%85%94", "https://www.instagram.com/shillahotels/"
        );

        shopRepository.save(shop1);
        shopRepository.save(shop2);
        shopRepository.save(shop3);
        // when
        PageRequest request = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "shopName"));
        Page<Shop> all = shopRepository.findAll(request);
        // then

    }






}