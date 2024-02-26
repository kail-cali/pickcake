package co.pickcake.mapapi.service;

import co.pickcake.common.entity.Address;
import co.pickcake.mapapi.response.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class MapSearchApiServiceTest {
    /*
    * 해당 테스트 클래스는 성능 테스트, 부하테스트 등 다양하게 사용되기 때문에 수정을 최소한으로 할것
    * 또한 만약 이곳에서 에러가 발생한다면 디펜던시, 버전 체크, Dto 변경한 건 없었는지 확인 할 것
    * */

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
    @DisplayName("")
    void api별성능테스트() {

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

    @Test
    @DisplayName("v2 api[success]: 카카오 Intercept RestTemplate map geo search ")
    void v2kakaoWtihInterceptRestTemplate() {
        //given
        Address address = Address.createAddress("서울", "동호로 249", "");
        //when
        KaKaoMapApiResponse response = mapSearchApiService.searchGeoWithRestTemplateInterceptOnKAKAO(address);
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

    @Test
    @DisplayName("v3 api[success]: 카카오 WebClient map geo search")
    void searchGeoWithWebClientKakaoNative() {
        //given
        Address address = Address.createAddress("서울", "동호로 249", "03342");
        //when
        KaKaoMapApiResponse response = mapSearchApiService.searchGeoWithWebClientNativeOnKAKAO(address);
        KaKaoMetaResponse meta = response.getMetaResponse();
        List<KaKaoDocumentResponse> documents = response.getDocumentResponses();
        for (KaKaoDocumentResponse document: documents) {
            System.out.println("document = " + document);
        }
        //then
        Assertions.assertThat(documents).isNotNull();
        Assertions.assertThat(meta).isNotNull();
        Assertions.assertThat(meta.getTotalCount()).isEqualTo(1);
        Assertions.assertThat(documents.getFirst().getLatitude()).isNotEqualTo(0.0);
        Assertions.assertThat(documents.getFirst().getLongitude()).isNotEqualTo(0.0);
    }
    @Test
    @DisplayName("v4 api[success]: 카카오 WebClient tune map geo search")
    void searchGeoWithWebClientKakaoTuning() {
        //given
        Address address = Address.createAddress("서울", "동호로 249", "03342");
        //when
        KaKaoMapApiResponse response = mapSearchApiService.searchGeoWithWebClientAddOnKAKAO(address);
        KaKaoMetaResponse meta = response.getMetaResponse();
        List<KaKaoDocumentResponse> documents = response.getDocumentResponses();

        for (KaKaoDocumentResponse document: documents) {
            System.out.println("document = " + document);
        }
        //then
        Assertions.assertThat(documents).isNotNull();
        Assertions.assertThat(meta).isNotNull();
        Assertions.assertThat(meta.getTotalCount()).isEqualTo(1);
        Assertions.assertThat(documents.getFirst().getLatitude()).isNotEqualTo(0.0);
        Assertions.assertThat(documents.getFirst().getLongitude()).isNotEqualTo(0.0);
    }
    @Test
    @DisplayName("v2 api[success]: 네이버 Native RestTemplate map geo search ")
    void v1NaverWithNativeRestTemplate() {
        //given
        Address address = Address.createAddress("서울", "동호로 249", "");
        //when
        NaverMapApiResponse response = mapSearchApiService.searchGeoWithRestTemplateNativeOnNAVER(address);
        List<NaverMapDocumentResponse> documents = response.getDocumentResponses();
        NaverMetaResponse meta = response.getMetaResponse();
        for (NaverMapDocumentResponse items: documents) {
            System.out.println("items = " + items);
        }
        //then
        Assertions.assertThat(documents).isNotNull();
        Assertions.assertThat(meta).isNotNull();
        Assertions.assertThat(meta.getTotalCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("v2 api[success]: 네이버 Intercept RestTemplate map geo search ")
    void v2naverWtihInterceptRestTemplate() {
        //given
        Address address = Address.createAddress("서울", "동호로 249", "");
        //when
        NaverMapApiResponse response = mapSearchApiService.searchGeoWithRestTemplateInterceptOnNAVER(address);
        List<NaverMapDocumentResponse> documents = response.getDocumentResponses();
        NaverMetaResponse meta = response.getMetaResponse();
        for (NaverMapDocumentResponse items: documents) {
            System.out.println("items = " + items);
        }
        //then
        Assertions.assertThat(documents).isNotNull();
        Assertions.assertThat(meta).isNotNull();
        Assertions.assertThat(meta.getTotalCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("v4 api[success]: 네이버 WebClient native map geo search")
    void searchGeoWithWebClientNaverNative() {
        // given
        Address address = Address.createAddress("서울", "동호로 249", "03344");
        // when
        NaverMapApiResponse response = mapSearchApiService.searchGeoWithWebClientNativeOnNAVER(address);
        List<NaverMapDocumentResponse> documents = response.getDocumentResponses();
        NaverMetaResponse meta = response.getMetaResponse();

        for (NaverMapDocumentResponse items: documents) {
            System.out.println("items = " + items);
        }
        //then
        Assertions.assertThat(documents).isNotNull();
        Assertions.assertThat(meta).isNotNull();
        Assertions.assertThat(meta.getTotalCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("currently used api[success]: 카카오 WebClient tune map geo search")
    public void searchGeoWithPreDefined() {
        // given
        Address address = Address.createAddress("서울", "동호로 249", "03344");
        // when
        KaKaoMapApiResponse response = mapSearchApiService.searchGeoOnKAKAO(address);
        KaKaoMetaResponse meta = response.getMetaResponse();
        List<KaKaoDocumentResponse> documents = response.getDocumentResponses();

        for (KaKaoDocumentResponse document: documents) {
            System.out.println("document = " + document);
        }
        //then
        Assertions.assertThat(documents).isNotNull();
        Assertions.assertThat(meta).isNotNull();

        Assertions.assertThat(meta.getTotalCount()).isEqualTo(1);
        Assertions.assertThat(documents.getFirst().getLatitude()).isNotEqualTo(0.0);
        Assertions.assertThat(documents.getFirst().getLongitude()).isNotEqualTo(0.0);
    }
    @Test
    @DisplayName("api 예외처리 [fail]: address 값이 null 로 들어갔을 떄")
    public void searchGeoWithPreDefinedFail() {
        // given
        // when
        //then
        Assertions.assertThatThrownBy(()-> mapSearchApiService.searchGeoOnKAKAO(null)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Currently used api[success]: 네이버 WebClient native map geo search")
    void searchGeoWithWebClientNaver() {
        // given
        Address address = Address.createAddress("서울", "동호로 249", "03344");
        // when
        NaverMapApiResponse response = mapSearchApiService.searchGeoOnNAVER(address);
        List<NaverMapDocumentResponse> documents = response.getDocumentResponses();
        NaverMetaResponse meta = response.getMetaResponse();
        for (NaverMapDocumentResponse items: documents) {
            System.out.println("items = " + items);
        }

        //then
        Assertions.assertThat(documents).isNotNull();
        Assertions.assertThat(meta).isNotNull();
        Assertions.assertThat(meta.getTotalCount()).isEqualTo(1);
    }
}