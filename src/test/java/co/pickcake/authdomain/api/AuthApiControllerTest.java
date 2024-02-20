package co.pickcake.authdomain.api;

import co.pickcake.authdomain.dto.SignupRequest;
import co.pickcake.authdomain.repository.MemberRepository;
import co.pickcake.authdomain.service.AuthService;
import co.pickcake.common.entity.Address;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthApiControllerTest {

    @Autowired private ObjectMapper objectMapper;
    @Autowired private MockMvc mockMvc;
    @Autowired private AuthService authService;
    @Autowired private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    @DisplayName("통합 기능 테스트[success]: 회원 가입이 정상적으로 동작하는지 확인")
    void signup() throws Exception {
        //given
//        SignupRequest request = SignupRequest.builder()
//                .userId("hail@gamil.com")
//                .email("hail@gmail.com")
//                .password("1234")
//                .userName("hail")
//                .address(Address.createAddress("seoul","yonsei", "31123"))
//                .build();
        SignupRequest request = SignupRequest.create("hail@gmail.com", "hail@gamil.com",
                                            "1234", "hail",
                                            Address.createAddress("seoul", "yonsei", "31123"));
        //when
        //then
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    @DisplayName("통합 기능 테스트[fail]: 중복으로 회원 가입 시 에러 발생 / 에러 정책 확인")
    void signupDuplicateMember() throws Exception {
        //given
        SignupRequest request1 = SignupRequest.create("hail@gmail.com", "hail@gamil.com",
                "1234", "hail",
                Address.createAddress("seoul", "yonsei", "31123"));

        SignupRequest requestDup = SignupRequest.create("hail@gmail.com", "hail@gamil.com",
                "1242343234", "hail",
                Address.createAddress("seoul", "yonsei", "31123"));
        //when
        // ex handler 정책 확인 하기

        //then
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request1)))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDup)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("DUPLICATED_USER_ALREADY_EXISTS"))
                .andExpect(jsonPath("$.message").value("DUPLICATED_USER_ALREADY_EXISTS"))
                .andDo(print());
    }

    @Test
    @DisplayName("통합 기능 테스트[success]: 회원 로그인 후, 회원 탈퇴 시 정상 동작 확인 ")
    void withDrawlSelf() throws Exception {

    }

}