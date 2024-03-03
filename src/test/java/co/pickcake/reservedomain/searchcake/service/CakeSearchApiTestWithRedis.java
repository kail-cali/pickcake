package co.pickcake.reservedomain.searchcake.service;


import co.pickcake.test.container.AbstractIntegrationContainerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CakeSearchApiTestWithRedis extends AbstractIntegrationContainerTest {
    /* redis 설정 시 유닛 테스트는 이곳에서 함 */

    @Autowired private CakeSearchService cakeSearchService;
    @Autowired private MockMvc mockMvc;

    @Test
    @DisplayName("상품 조회 redis unit test[success]")
    void searchFindAllTest() {

    }

}
