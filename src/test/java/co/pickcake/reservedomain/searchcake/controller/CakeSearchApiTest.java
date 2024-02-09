package co.pickcake.reservedomain.searchcake.controller;

import co.pickcake.aop.util.ErrorCode;
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
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/* 정책 테스트는 항상 변경 될 때마다 테스트 반드시 확인할 것 */
/* response 에 대한 api 데이터 검증은 통합테스트 확인 */
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
    @DisplayName("api 검증[success]: 전체 상품을 조회 테스트 ")
    void searchAllCakeTestParam() throws Exception {
        //given
        ResultActions result = mockMvc.perform(get("/api/cake")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("offset", String.valueOf(0))
                        .param("limit", String.valueOf(2))
        );
        //when
        //then
        result.andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    @DisplayName("api 검증[success]: 전체 상품을 조회 테스트 ")
    void searchAllCakeTestBody() throws Exception {
        //given
        ResultActions response = mockMvc.perform(get("/api/cake/call")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"offset\": 0, \"limit\": 10 }")
        );

        //when

        //then
        /* mock 이라서 실제 Dto 가 오지는 않기 때문에 통합 테스트 때 파라미터 확인 필요 */
        response.andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    @DisplayName("api Exception 정책 테스트[fail]: 전체 상품 조회, 잘못된 파라미터 시 Bad request")
    public void searchAllCakeTestBodyBadRequest() throws Exception {
        //given
        ResultActions response = mockMvc.perform(get("/api/cake/call")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"offset\": -1, \"limit\": 10 }")
        );
        //when
        //then
        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(ErrorCode.METHOD_ARGUMENT_NOT_VALID.toString()))
                .andDo(print());

    }
    @Test
    @DisplayName("api 검증[success]: 이름으로 상품 조회 ")
    void searchByBrand() throws Exception {
        //given
        ResultActions result = mockMvc.perform(get("/api/cake/brand")
                .contentType(MediaType.APPLICATION_JSON)
                .param("brand", "some brand"));
        //when
        //then
        result.andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    @DisplayName("api 검증[fail]: Not Empty 이름으로 상품 조회 ")
    void searchByBrandFailName() throws Exception {
        //given
        ResultActions result = mockMvc.perform(get("/api/cake/brand")
                .contentType(MediaType.APPLICATION_JSON)
                .param("brand", ""));
        //when
        //then
        result.andExpect(status().isBadRequest())
                .andDo(print());
    }
    @Test
    @DisplayName("api 검증[fail]:  잘못된 파라미터로 카테고리로 api 조회 시 결과")
    void searchBySingleCategoryFail() throws Exception {
        //given
        ResultActions response = mockMvc.perform(get("/api/cake/category")
                .contentType(MediaType.APPLICATION_JSON)
                        .param("offset", String.valueOf(-1))
        );
        //when
        //then
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }
    @Test
    @DisplayName("api 검증[success]: 디폴트 파라미터로 카테고리로 api 조회 시 결과")
    void searchBySingleCategoryDefault() throws Exception {
        //given
        ResultActions response = mockMvc.perform(get("/api/cake/category")
                .contentType(MediaType.APPLICATION_JSON)
        );
        //when
        //then
        response.andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    @DisplayName("api 검증[fail]: blank 카테고리 파라미터로 카테고리로 api 조회 시 결과")
    void searchBySingleCategoryFailCategory() throws Exception {
        //given
        ResultActions response = mockMvc.perform(get("/api/cake/category")
                .contentType(MediaType.APPLICATION_JSON)
                .param("categoryName", " ")
        );
        //when
        //then
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }


}