package co.pickcake.reservation.repository;

import co.pickcake.reservation.domain.item.Cake;
import co.pickcake.reservation.searchcake.repository.CakeAdminRepository;
import co.pickcake.reservation.searchcake.repository.CakeUserRepository;
import co.pickcake.util.TestInitDB;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class CakeUserRepositoryTest {

    @Autowired
    CakeUserRepository cakeRepository;

    @Autowired
    CakeAdminRepository cakeAdminRepository;

    @Autowired
    private TestInitDB testInitDB;

    @Test
    @DisplayName("데이터 검증[success]: 브랜드 별 조회")
    @Transactional
    public void findByBrand() {
        //given
        testInitDB.dbInitWithItems();
        //when
        List<Cake> byBrand = cakeRepository.findByBrand("신라호텔");
        //then
        Assertions.assertThat(byBrand.size())
                .isEqualTo(2);
    }




}