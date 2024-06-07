package co.pickcake.reservation.searchcake.service;


import co.pickcake.reservation.searchcake.response.PickCakeApiResponse;
import co.pickcake.reservation.searchcake.response.PickCakeDocumentResponse;
import co.pickcake.reservation.searchcake.response.PickCakeMetaResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
class CakeSearchApiRequestServiceTest {

    @Autowired private CakeSearchApiRequestService apiRequestService;
    @Autowired private ObjectMapper objectMapper;

    @MockBean
    private PickCakeUriBuilderService pickCakeUriBuilderService;
    private MockWebServer mockWebServer;
    private MockWebUriBuilder mockWebUriBuilder;
    public static class MockWebUriBuilder {
        public String LOCAL_REQUEST_GATE_WAY = "http://localhost";
        public URI builderUrlByDefault(Integer offset, Integer limit) {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(LOCAL_REQUEST_GATE_WAY);
            uriBuilder.queryParam("offset", offset);
            uriBuilder.queryParam("limit", limit);
            URI uri = uriBuilder.build().encode().toUri();
            return uri;
        }
    }
    public static class MockWebResponseBuilder {
        public static PickCakeApiResponse create() {
            PickCakeMetaResponse metaExpected = PickCakeMetaResponse.create(2);
            PickCakeDocumentResponse document1 = new PickCakeDocumentResponse(100L, "생크림케이크", 100000);
            PickCakeDocumentResponse document2 = new PickCakeDocumentResponse(201L, "딸기케이크", 150000);
            ArrayList<PickCakeDocumentResponse> documentsExpected = new ArrayList<>();
            documentsExpected.add(document1);
            documentsExpected.add(document2);
            return PickCakeApiResponse.create(HttpStatus.OK, metaExpected, documentsExpected);
        }
    }


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
        mockWebUriBuilder = new MockWebUriBuilder();
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

    @Test
    @DisplayName("v1 api[success]: 외부로 나가는 api 검증")
    void searchCakesRequestTest() throws JsonProcessingException {
        //given
        PickCakeApiResponse responseExpected = MockWebResponseBuilder.create();
        Mockito.when(pickCakeUriBuilderService.builderUrlByDefault(0,10))
                .thenReturn(mockWebUriBuilder.builderUrlByDefault(0, 10));
        //when
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(objectMapper.writeValueAsString(responseExpected)));
        PickCakeApiResponse response = apiRequestService.searchCakes(0, 10);
        //then
        Assertions.assertThat(response).isNotNull();
        System.out.println("response = " + response);
    }
    @Test
    @DisplayName("v1 api[success]: retry test")
    void searchCakesRequestTest2() throws JsonProcessingException {
        //given
        PickCakeApiResponse responseExpected = MockWebResponseBuilder.create();
        Mockito.when(pickCakeUriBuilderService.builderUrlByDefault(0,10))
                .thenReturn(mockWebUriBuilder.builderUrlByDefault(0, 10));
        //when
        mockWebServer.enqueue(new MockResponse().setResponseCode(504));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(objectMapper.writeValueAsString(responseExpected)));
        PickCakeApiResponse response = apiRequestService.searchCakes(0, 10);
        //then
        Assertions.assertThat(response).isNotNull();
        System.out.println("response = " + response);
    }
}