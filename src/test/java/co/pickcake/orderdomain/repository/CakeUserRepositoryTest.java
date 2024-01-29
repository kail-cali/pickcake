package co.pickcake.orderdomain.repository;

import co.pickcake.orderdomain.entity.item.Cake;
import co.pickcake.orderdomain.searchcake.repository.CakeAdminRepository;
import co.pickcake.orderdomain.searchcake.repository.CakeUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CakeUserRepositoryTest {


    @Autowired
    CakeUserRepository cakeRepository;

    @Autowired
    CakeAdminRepository cakeAdminRepository;

    @Test
    @DisplayName("브랜드 별 조회")
    @Transactional
    public void findByBrand() {
        Cake item = new Cake();
        item.setName("red velvet cake");
        item.setBrand("신라호텔");

        Cake item2 = new Cake();
        item2.setName("strawberry whit cream cake");
        item2.setBrand("시그니엘");

        Cake item3 = new Cake();
        item3.setName("choco cake");
        item3.setBrand("신라호텔");

        //when
        cakeAdminRepository.save(item);
        cakeAdminRepository.save(item2);
        cakeAdminRepository.save(item3);

        //then
        Assertions.assertThat(cakeRepository.findByBrand("신라호텔").size())
                .isEqualTo(3);

    }




}