package co.pickcake.mapapi.service;

import co.pickcake.common.entity.Address;
import co.pickcake.mapapi.response.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

/* FIXME test 시 session hang, 왜 인지 아직 모르겠음 ;;; */

@SpringBootTest
@AutoConfigureMockMvc
public class MapSearchApiServiceRetryTest {

    @Autowired private MapSearchApiService mapSearchApiService;
    @Autowired private MockMvc mockMvc;

    private MockWebServer mockWebServer;
    private MockWebUriBuilder mockWebUriBuilder;

    /* TODO LAZY MOCKBEAN 검토 필요 */
    @MockBean
    private KaKaoUriBuilderService kaKaoUriBuilderService;
    @MockBean
    private NaverUriBuilderService naverUriBuilderService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockWebServer = new MockWebServer();
        try {
            mockWebServer.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int port = mockWebServer.getPort();
        System.out.println("Mock Web Server port = " + port);
        HttpUrl url = mockWebServer.url("/");
        System.out.println("url = " + url);
        mockWebUriBuilder = new MockWebUriBuilder(); /* 별도로 쿼리 생성해야한다면 이 부분 반드시 바꿀 것 */
        mockWebUriBuilder.LOCAL_REQUEST_GATE_WAY = mockWebServer.url("/").uri().toString();
    }
    @AfterEach
    void tearDown() {
        try {
            mockWebServer.shutdown();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /* TODO 3rd-party 마다 MockMvc class 따로 생성해야할지는 테스트 스펙에 따라 달려 있음.
    *   지금은 예외처리 부분만 테스트하고 있기 때문에 Mock class 하나로 모두 바꿔치기 가능하지만
    *   기능 테스트 단위로 넘어갈 경우, ToOne 으로 생성 필요.
    *   생각했던 것 보다 코드 양이 자꾸 늘어나서 더 최적화된 보일러 플레이트 코드 필요 */
    /* SEE MockWebUriBuilder at BeforeEach */
    public static class MockWebUriBuilder implements BaseUriBuilder  {
        public String LOCAL_REQUEST_GATE_WAY = "http://localhost";
        @Override
        public URI builderUrlByAddress(Address address) {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(LOCAL_REQUEST_GATE_WAY);
            uriBuilder.queryParam("query", address.toSimpleString());
            URI uri = uriBuilder.build().encode().toUri();
            return uri;
        }

        @Override
        public URI builderUrlByKeyWord(Double longitude, Double latitude, Double radius, String keyword) {
            return null;
        }
    }
    public static class MockWebResponseBuilder {

        public static KaKaoMapApiResponse createKakaoResponse() {
            KaKaoMetaResponse metaExpected = KaKaoMetaResponse.create(1);
            KaKaoDocumentResponse documentExpected = KaKaoDocumentResponse.create("신라호텔",
                    "서울 동호로 249", 100D, 37D, 0D);
            ArrayList<KaKaoDocumentResponse> documentsExpected = new ArrayList<>();
            documentsExpected.add(documentExpected);
            return KaKaoMapApiResponse.create(HttpStatus.OK, metaExpected, documentsExpected);
        }

        public static NaverMapApiResponse createNaverResponse() {
            NaverMetaResponse metaExpected = NaverMetaResponse.create(1, 1, 1);
            NaverMapDocumentResponse documentExpected = NaverMapDocumentResponse.create("동호로 249",
                    "서울시 중구", "100", "30", 0D);
            ArrayList<NaverMapDocumentResponse> documentsExpected = new ArrayList<>();
            documentsExpected.add(documentExpected);
            return NaverMapApiResponse.create(HttpStatus.OK, metaExpected, documentsExpected);
        }
    }
    /* SEE MockWebUriBuilder at BeforeEach */
    @Test
    @DisplayName("v2 api[success]: 예외처리 카카오 Intercept RestTemplate map geo search Retry Test")
    void v2kakaoWtihInterceptRestTemplate() throws JsonProcessingException {
        //given
        KaKaoMapApiResponse responseExpected = MockWebResponseBuilder.createKakaoResponse();
        Address address = Address.createAddress("서울", "동호로 249", "");
        Mockito.when(kaKaoUriBuilderService.builderUrlByAddress(address))
                .thenReturn(mockWebUriBuilder.builderUrlByAddress(address));
        //when
        mockWebServer.enqueue(new MockResponse().setResponseCode(504));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                        .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(objectMapper.writeValueAsString(responseExpected)));
        KaKaoMapApiResponse response = mapSearchApiService.searchGeoWithRestTemplateInterceptOnKAKAO(address);
        //then
        Assertions.assertThat(response).isNotNull(); // retry 요청 횟수가 2라면 반드시 성공해야함.
        Assertions.assertThat(response.getMetaResponse()).isNotNull();
        Assertions.assertThat(response.getMetaResponse().getTotalCount()).isEqualTo(1);
        Assertions.assertThat(response.getDocumentResponses().size()).isEqualTo(1);
        Assertions.assertThat(response.getDocumentResponses().getFirst().getLatitude()).isEqualTo(100D);
        Assertions.assertThat(response.getDocumentResponses().getFirst().getLongitude()).isEqualTo(37D);
    }
    @Test
    @DisplayName("v2 api[success]: 예외처리 네이버 Intercept RestTemplate map geo search Retry Test ")
    void v2naverWtihInterceptRestTemplate() throws JsonProcessingException {
        //given
        NaverMapApiResponse responseExpected = MockWebResponseBuilder.createNaverResponse();
        Address address = Address.createAddress("서울", "동호로 249", "");
        Mockito.when(naverUriBuilderService.builderUrlByAddress(address))
                .thenReturn(mockWebUriBuilder.builderUrlByAddress(address));
        //when
        mockWebServer.enqueue(new MockResponse().setResponseCode(504));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(objectMapper.writeValueAsString(responseExpected)));

        NaverMapApiResponse response = mapSearchApiService.searchGeoWithRestTemplateInterceptOnNAVER(address);
        //then
        Assertions.assertThat(response).isNotNull(); // retry 요청 횟수가 2라면 반드시 성공해야함.
        Assertions.assertThat(response.getMetaResponse()).isNotNull();
        Assertions.assertThat(response.getMetaResponse().getTotalCount()).isEqualTo(1);
        Assertions.assertThat(response.getDocumentResponses().size()).isEqualTo(1);
        Assertions.assertThat(response.getDocumentResponses().getFirst().getLatitude()).isEqualTo("100");
        Assertions.assertThat(response.getDocumentResponses().getFirst().getLongitude()).isEqualTo("30");
    }

}
