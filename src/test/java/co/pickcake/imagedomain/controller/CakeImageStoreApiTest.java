package co.pickcake.imagedomain.controller;


import co.pickcake.authdomain.repository.MemberRepository;
import co.pickcake.authdomain.service.MemberService;
import co.pickcake.config.TestMockUser;
import co.pickcake.imagedomain.service.ImageServer;
import co.pickcake.imagedomain.service.ImageStoreService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@WebMvcTest(CakeImageStoreApi.class)
@SpringBootTest
@TestMockUser(role="ROLE_ADMIN")
@AutoConfigureMockMvc
class CakeImageStoreApiTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ImageServer imageServer;
    @MockBean
    private MemberRepository memberRepository;
    @AfterEach
    void clean() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("api 검증[success]: 파일을 특정 uuid 이름으로 매핑하여 서버에 저장하는 api 호출 테스트")
    public void uploadWithApiTest() throws Exception {
        String fileName = "s_x1";
        String ext = "png";
        String filePath = "/Users/george/dev/raw_images/s_x1.png";
        String storeName = "new.png";

        FileInputStream fileInputStream = new FileInputStream(filePath);

        MockMultipartFile storedImage = new MockMultipartFile("image",
                fileName + "." + ext,
                ext,
                fileInputStream
        );
        mockMvc.perform(
                multipart("/api/image/store")
                        .file(storedImage)
                        .param("storeName", storeName)
        ).andExpect(status().isOk());

    }

    @Test
    @DisplayName("api 검증[fail]: 잘못된 파라미터 네임으로 요청 보냈을 때 실패 처리")
    public void uploadWithApiTestNotValid() throws Exception {
        String fileName = "s_x1";
        String ext = "png";
        String filePath = "/Users/george/dev/raw_images/s_x1.png";
        String storeName = "new.png";

        FileInputStream fileInputStream = new FileInputStream(filePath);

        MockMultipartFile storedImage = new MockMultipartFile("imageNotValidParam",
                fileName + "." + ext,
                ext,
                fileInputStream
        );
        mockMvc.perform(
                multipart("/api/image/store")
                        .file(storedImage)
                        .param("storeName", storeName)
        ).andExpect(status().isBadRequest());

    }







}