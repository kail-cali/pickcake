package co.pickcake.imagedomain.service;

import co.pickcake.imagedomain.entity.ImageFile;
import co.pickcake.imagedomain.repository.CakeImageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional

class ImageStoreServiceTest {


    @Autowired ImageStoreService imageStoreService;
    @Autowired CakeImageRepository cakeImageRepository;

    @Test
    @DisplayName("이미지 저장 테스트 1")
    public void saveImageFile_v1() {

//        ImageFile imageFile = imageStoreService.storeFile();
    }






}