package co.pickcake.reservedomain.searchcake.controller;

import co.pickcake.reservedomain.searchcake.repository.CakeSearchRepository;
import co.pickcake.reservedomain.searchcake.repository.CakeUserRepository;
import co.pickcake.reservedomain.searchcake.service.CakeSearchService;
import groovy.transform.AutoImplement;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CakeSearchApi.class)
class CakeSearchApiTest {

    @Autowired
    private  MockMvc mockMvc;


    @MockBean
    private CakeSearchService cakeSearchService;
    @MockBean
    private  CakeUserRepository cakeUserRepository;
    @MockBean
    private  CakeSearchRepository cakeSearchRepository;

    @Test
    @DisplayName("api 검정: ")
    void searchAllCakeTest() throws Exception {

//        server.expect(MockRestRequestMatchers
//                .requestTo("/api/cake"))
//                .andRespond(MockRestResponseCreators.withSu);

//        Assertions.assertThatThrownBy(
//        ()-> mockMvc.perform(
//                get("http://localhost:8080/api/cake")
////                        .param("offset", String.valueOf(0))
////                        .param("limit", String.valueOf(10))
//        )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect()
//        );


    }

    @Test
    void searchByBrand() {
    }

    @Test
    @DisplayName("api 검정: 존재하는 필터로 조회 시 응답 확인")
    void searchBySingleCategory() {

//        mockMvc.perform(
//
//                )
//                .andExpect(status().isOk());

    }

    @Test
    void searchByName() {
    }
}