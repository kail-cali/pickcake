package co.pickcake.mapapi.service;

import co.pickcake.common.entity.Address;
import co.pickcake.mapapi.response.KaKaoDocumentResponse;
import co.pickcake.mapapi.response.KaKaoMapApiResponse;
import co.pickcake.mapapi.response.KaKaoMetaResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class MapSearchApiServiceMockTest {
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
    @DisplayName("v1 api[success]: 카카오 Native RestTemplate  map geo search ")
    void v1kakaoWithNativeRestTemplate() {
        //given
        Address address = Address.createAddress("서울", "동호로 249", "");

        //when
        KaKaoMapApiResponse response = mapSearchApiService.searchGeoWithRestTemplateNativeOnKAKAO(address);
        List<KaKaoDocumentResponse> documents = response.getDocumentResponses();
        KaKaoMetaResponse meta = response.getMetaResponse();

        for (KaKaoDocumentResponse items: documents) {
            System.out.println("items = " + items);
        }

        //then
        Assertions.assertThat(documents).isNotNull();
        Assertions.assertThat(meta).isNotNull();

        Assertions.assertThat(meta.getTotalCount()).isEqualTo(1);
        Assertions.assertThat(documents.getFirst().getLatitude()).isNotEqualTo(0.0);
        Assertions.assertThat(documents.getFirst().getLongitude()).isNotEqualTo(0.0);
    }

}
