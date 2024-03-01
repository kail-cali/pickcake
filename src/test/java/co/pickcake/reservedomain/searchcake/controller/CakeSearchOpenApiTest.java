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
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/* ISU-#28 (prevented)
    [현상]
    테스트가 모두 종료되어도 class level 에서 jdbc session Hang 발생,
    연쇄 효과로 원래 정상적으로 테스트 클래스가 종료되면 testContainer 삭제 call invoke 되어야 하는데 shutdown 으로 종료되어
    Test container 삭제 스크립트도 실행되지 않았음.
    [원인]
    컨테이너 테스트 session hang 원인이 TestMockUser 의 라이프 사이클과 트랜잭션 롤백이 제대로 수행되지 않았던 문제가 있었음.
    - TestMock User 도 Member entity 에 데이터를 insert 하는데 별도로 롤백해주는 부분이 없었으며 라이프 사이클 관리 설정이 꼬였음
    - TestMock User 의 라이프 사이클은 RunTime 으로 한 상태에서 class 레벨로 annotation 을 걸게 되면서, spring boot 가 모든 테스트가 종료
      한 뒤에 rollback 해주면서 해당 annotation 을 class 레벨로 사용하던 test class 는 session hang 이 걸렸고, test container 를
      종료시키는 After ALL 이 제대로 invoke 되지 않았음.
    - 현상으로만 보면 timeout 으로 종료되어 원인을 왜곡시켰음.
    [조치]
    Test Mock User 의 라이프사이클을 Runtime 으로 둔 상태에서 Method 단위로 라이프 사이클 관리할 수 있도록 수정.
    - method 를 실행 할 때 마다 TestMockUser 를 다시 생성하고 method 종료 한 뒤에 트랜잭션 롤백이 이루어질 수 있도록 수정.
    - 부차적으로 Test Mock User 의 라이프사이클을 RUNTIME 으로 하니 트랜잭션 커밋되었고 이 때문에 다른 테스트에도 영향을 주었던 문제도 함께 해결
    */


@SpringBootTest
@AutoConfigureMockMvc
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
    @Transactional
    @TestMockUser(role = "ROLE_USER")
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
    @Transactional
    @TestMockUser(role = "ROLE_USER")
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
    @Transactional
    @TestMockUser(role = "ROLE_USER")
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
    @Transactional
    @TestMockUser(role = "ROLE_USER")
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
    @Transactional
    @TestMockUser(role = "ROLE_USER")
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