package co.pickcake.reservation.searchcake.controller;


import co.pickcake.mapapi.service.MapSearchApiService;
import co.pickcake.reservation.searchcake.service.CakeSearchService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/* 정책 테스트는 항상 변경 될 때마다 테스트 반드시 확인할 것 */
/* response 에 대한 api 데이터 검증은 통합테스트 확인 */
@WebMvcTest(CakeSearchApi.class)
@WithMockUser
class CakeSearchApiTest {

    @Autowired
    private  MockMvc mockMvc;

    @MockBean
    private CakeSearchService cakeSearchService;
    @MockBean
    private MapSearchApiService mapSearchApiService;

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
    @DisplayName("api 검증[fail]: Blank 이름으로 상품 조회 ")
    void searchByBrandFailName() throws Exception {
        //given
        ResultActions result = mockMvc.perform(get("/api/cake/brand")
                .contentType(MediaType.APPLICATION_JSON)
                .param("brand", " "));
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
    @Test
    @DisplayName("api 검증[success]: 상세 아이템 조회 api 테스트")
    public void searchCakeDetails() throws Exception{
        //given
        //when
        ResultActions response = mockMvc.perform(
                get("/api/cake/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("itemId", "1")
        );
        //then
        response.andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    @DisplayName("api 검증[fail]: 빈 아이템 아이디로 상세 아이템 조회 api 테스트")
    public void searchCakeDetailsFail1() throws Exception{
        //given
        //when
        ResultActions response = mockMvc.perform(
                get("/api/cake/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("itemId", "")
        );
        //then
        response.andExpect(status().isBadRequest())
//                .andExpect(re)
//                .andExpect(jsonPath("code").value(ErrorCode.METHOD_ARGUMENT_NOT_VALID.toString()))
                .andDo(print());
    }

    @Test
    @DisplayName("api 검증[fail]: 공백의 아이템 아이디값으로 상세 아이템 조회 api 테스트")
    public void searchCakeDetailsFail2() throws Exception{
        //given
        //when
        ResultActions response = mockMvc.perform(
                get("/api/cake/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("itemId", " ")
        );
        //then
        response.andExpect(status().isBadRequest())
//                .andExpect(re)
//                .andExpect(jsonPath("code").value(ErrorCode.METHOD_ARGUMENT_NOT_VALID.toString()))
                .andDo(print());
        //Resolved Exception:
        //             Type = java.lang.NumberFormatException
    }




}