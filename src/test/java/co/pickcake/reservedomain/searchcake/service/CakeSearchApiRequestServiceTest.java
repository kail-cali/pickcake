package co.pickcake.reservedomain.searchcake.service;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import co.pickcake.aop.apigateway.ApiGatewayConfig;
import co.pickcake.reservedomain.searchcake.dto.ResultDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@WithMockUser(roles = "USER")
class CakeSearchApiRequestServiceTest {

    @Autowired
    private  CakeSearchApiRequestService apiRequestService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void searchCakesRequestTest() {

//        ResultDto resultDto = apiRequestService.searchCakes(0, 10);


    }
}