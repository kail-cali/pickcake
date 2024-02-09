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
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    @DisplayName("api 검증: 전체 상품을 조회 테스트 ")
    void searchAllCakeTest() throws Exception {
        //given

        //when

        //then
            mockMvc.perform(get("/api/cake")
                        .contentType(MediaType.APPLICATION_JSON)
                                    .param("offset", String.valueOf(0))
                                    .param("limit", String.valueOf(2))
//                        .content()
                    )
                    .andExpect(status().isOk())
//                    .andExpect()
                    .andDo(print());
    }

    @Test
    @DisplayName("api 검증: 이름으로 상품 조회 ")
    void searchByBrand() {
        //given

        //when

        //then


    }

    @Test
    @DisplayName("api 검정: 존재하는 카테고리로 api 조회 시 결과")
    void searchBySingleCategory() {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("api 검정: 존재하지 않는 카테고리로 api 조회 시 결과")
    void searchBySingleCategoryNull() {
        //given

        //when

        //then
    }

}