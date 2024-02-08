package co.pickcake.reservedomain.searchcake.service;

import co.pickcake.InitDb;
import co.pickcake.reservedomain.searchcake.dto.CakeCategorySearch;
import co.pickcake.testconfig.InitCreate;
import jakarta.persistence.Access;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static co.pickcake.testconfig.InitCreate.DBINITWITHIMAGEITEM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CakeSearchServiceTest {

    @Autowired
    private CakeSearchService cakeSearchService;

    @Autowired
    private InitDb initDb;

    private Map<InitCreate , Integer> mp;

    /* NEEDTOFIX 현재 test 용 클래스가 운영서버에 들어가 있는 상황이라 이러한 방식으로 매핑을 하였음 */

    public void setCreateConfig() {
        if (mp == null){
            mp = new HashMap<>();
            mp.put(DBINITWITHIMAGEITEM,1);
            mp.put(InitCreate.DBINITWITHORDER,2);
            mp.put(InitCreate.DBINITWITHITEM,3);
        }

    }

    @Test
    void findAll() {
    }

    @Test
    void findByBrand() {
    }

    @Test
    @DisplayName("데이터 검증: 카테고리 별 조회 되는지 테스트")
    void findBySingleCategory() {

        //given
        setCreateConfig();
        Integer testCaseSize = initDb.initForTest(mp.get(DBINITWITHIMAGEITEM));


        //when
        List<CakeCategorySearch> bySingleCategory = cakeSearchService.findBySingleCategory(0, 10, "23 시즌 크리스마스");


        //then

        assertThat(bySingleCategory.size()).isEqualTo(4);

        bySingleCategory.stream()
                .map(o -> assertThat(o.getBrand()).isNotEmpty())
                .collect(Collectors.toList());

    }

    @Test
    void findByNameOnLike() {
    }
}