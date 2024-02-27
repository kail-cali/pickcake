package co.pickcake.reservedomain.searchcake.controller;

import co.pickcake.aop.util.ErrorCode;
import co.pickcake.authdomain.repository.MemberRepository;
import co.pickcake.config.TestMockUser;
import co.pickcake.reservedomain.searchcake.repository.CakeSearchRepository;
import co.pickcake.reservedomain.searchcake.repository.CakeUserRepository;
import co.pickcake.reservedomain.searchcake.service.CakeSearchService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@TestMockUser(role = "ROLE_USER")
class CakeSearchOpenApiTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CakeSearchService cakeSearchService;
    @MockBean
    private CakeUserRepository cakeUserRepository;
    @MockBean
    private CakeSearchRepository cakeSearchRepository;

    @MockBean
    private MemberRepository memberRepository;
    @AfterEach
    void clean() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("api 검증[success]: 페이징 테스트")
    void searchCake() throws  Exception {
        //given
        ResultActions response = mockMvc.perform(
                get("/openapi/cake")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"offset\": 0, \"limit\": 10 }")
        );

        //then
        response.andExpect(status().isOk()).andDo(print());
    }

    @Test
    @DisplayName("api 검증[success]: 전체 상품을 조회 테스트 ")
    void searchAllCakeTestBody() throws Exception {
        //given
        ResultActions response = mockMvc.perform(get("/openapi/cake/call")
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
        ResultActions response = mockMvc.perform(get("/openapi/cake/call")
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
    @DisplayName("api 검증[success]: requestBody 로 요청을 보낼 시 http status 확인")
    void searchBySingleCategoryCallSuccess() throws Exception {
        //given
        String  requestBody =  "{\"categoryName\": \"생일\", \"offset\": 0 }";
        //when
        ResultActions response = mockMvc.perform(
                get("/openapi/cake/category/call")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );
        //then
        response.andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    @DisplayName("api 검증[fail]: requestBody validate 동작 테스트 ")
    void searchBySingleCategoryCallFail() throws Exception {
        //given
        String  requestBody =  "{\"categoryName\": \"생일\", \"offset\": -1, \"limit\":100 }";
        //when
        ResultActions response = mockMvc.perform(
                get("/openapi/cake/category/call")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );
        //then
        // 응답이 bad request 로 파라미터의 validation 검사 시 실패해야 한다.
        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(ErrorCode.METHOD_ARGUMENT_NOT_VALID.toString()))
                .andDo(print());
    }

}