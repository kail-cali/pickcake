package co.pickcake.reservedomain.searchcake.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest

@Transactional
class CakeAdminRepositoryTest {

    private MockMvc mockMvc;
    @Autowired private  CakeAdminRepository cakeAdminRepository;

    @Test
    @DisplayName("데이터 검정: 데이터가 정상 저장되는지 테스트")
    public void saveTest() {

    }
    @Test
    @DisplayName("데이터 검증: 잘못된 데이터가 저장될 때 에러 테스트")
    public void saveAbnormalData() {

    }

}