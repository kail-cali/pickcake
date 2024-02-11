package co.pickcake;

import co.pickcake.reservedomain.entity.ReserveInfo;
import co.pickcake.reservedomain.entity.item.Cake;
import co.pickcake.shopdomain.entity.Shop;
import co.pickcake.sns.entity.SNS;
import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/* entity 에 대한 생성 편의 메서드 테스트 모음 */
@SpringBootTest
@Transactional(readOnly = true)
public class StaticCreateEntityTest {

    /* NO BEAN ! Just for test list */
    private Shop shop;
        private SNS sns; // -> naver, instar
        private ReserveInfo reserveInfo;
    private Cake cake;

    @Test
    @DisplayName("생성자 테스트: 가게 예약 정보 static 으로 생성")
    void createReserveInfo() {
        // given
        LocalTime open = LocalTime.of(10, 0);
        LocalTime close = LocalTime.of(16, 0);
        //when
        ReserveInfo reserveInfo = ReserveInfo.create(false,false, 2, open, close);
        //then

    }

    @Test
    @DisplayName("생성자 테스트: 가게 정보 생성 테스트, 최소 정보로 생성")
    public void shopCreate1() {
        Shop shop1 = Shop.createShop("신라 호텔", "https://www.shillahotels.com/membership/inquires/aboutShilla/memSeoulHotel.do",
                "02-301-1111", "seoul", "중구", "3000",
                false, true, 2,
                LocalTime.of(10, 0), LocalTime.of(16, 0)
        );
    }
    @Test
    @DisplayName("생성자 테스트: 가게 정보 생성 테스트, 전체 정보로 생성")
    public void shopCreate3() {
        Shop shop1 = Shop.createShop("신라 호텔", "https://www.shillahotels.com/membership/inquires/aboutShilla/memSeoulHotel.do",
                "02-301-1111", "seoul", "중구", "3000",
                false, true, 2,
                LocalTime.of(10, 0), LocalTime.of(16, 0),
                "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EC%8B%A0%EB%9D%BC%ED%98%B8%ED%85%94", "https://www.instagram.com/shillahotels/"
        );
        System.out.println("shop1 = " + shop1);



    }
}
