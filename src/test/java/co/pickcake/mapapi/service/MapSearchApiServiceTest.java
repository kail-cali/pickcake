package co.pickcake.mapapi.service;

import co.pickcake.common.entity.Address;
import co.pickcake.mapapi.response.DocumentResponse;
import co.pickcake.mapapi.response.KaKaoMapApiResponse;
import co.pickcake.mapapi.response.MetaResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;


/* TODO  MockMvc API 요청 서버 만들기, api key도 테스트에서 계속 사용되는 게 불편하고,  테스트 돌리다가 api 일일 요청량 넘음 ;; */
@SpringBootTest
@AutoConfigureMockMvc
class MapSearchApiServiceTest {

    @Autowired
    private MapSearchApiService mapSearchApiService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    @DisplayName("외부 api[success]: 카카오 지도에서 데이터 연동하기 ")
    void v1kakao() {
        //given
        Address address = Address.createAddress("서울", "동호로 249", "");

        //when
        KaKaoMapApiResponse response = mapSearchApiService.v1kakao(address);
        List<DocumentResponse> documents = response.getDocumentResponses();
        MetaResponse meta = response.getMetaResponse();

        for (DocumentResponse items: documents) {
            System.out.println("items = " + items);
        }

        //then
        Assertions.assertThat(documents).isNotNull();
        Assertions.assertThat(meta).isNotNull();

        Assertions.assertThat(meta.getTotalCount()).isEqualTo(1);
        Assertions.assertThat(documents.getFirst().getLatitude()).isNotEqualTo(0.0);
        Assertions.assertThat(documents.getFirst().getLongitude()).isNotEqualTo(0.0);
    }

    @Test
    void v2kakao() {
    }
}