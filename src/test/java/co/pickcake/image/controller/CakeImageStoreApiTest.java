package co.pickcake.image.controller;


import co.pickcake.auth.repository.MemberRepository;

import co.pickcake.config.TestMockUser;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import org.springframework.test.web.servlet.MockMvc;


import java.io.FileInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/* CHECK ISU-#28 */
//@WebMvcTest(CakeImageStoreApi.class)
@SpringBootTest
@AutoConfigureMockMvc
class CakeImageStoreApiTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired private MemberRepository memberRepository;

    @AfterEach
    void clean() {
        memberRepository.deleteAll();
    }

    @Test
    @TestMockUser(role="ROLE_ADMIN")
    @DisplayName("api 검증[success]: 파일을 특정 uuid 이름으로 매핑하여 서버에 저장하는 api 호출 테스트")
    public void uploadWithApiTest() throws Exception {
        String fileName = "s_x1";
        String ext = "png";
        String filePath = "/Users/george/dev/raw_images/s_x1.png";
        String storeName = "test-new.png";

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
    @TestMockUser(role="ROLE_ADMIN")
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