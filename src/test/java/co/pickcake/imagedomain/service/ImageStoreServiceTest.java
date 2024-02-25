package co.pickcake.imagedomain.service;

import co.pickcake.imagedomain.entity.CakeImages;
import co.pickcake.imagedomain.entity.ImageFile;
import co.pickcake.imagedomain.repository.CakeImageRepository;
import co.pickcake.reservedomain.entity.item.Cake;
import co.pickcake.testconfig.TestDataItem;
import co.pickcake.util.TestInitDB;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ImageStoreServiceTest {


    @Autowired ImageStoreService imageStoreService;
    @Autowired CakeImageRepository cakeImageRepository;
    @Autowired TestInitDB testInitDB;

    @Test
    @DisplayName("기능 테스트[success]: 이미지 메타데이터가 정상적으로 저장되는 지 확인")
    @Transactional
    public void saveImageFile_v1() {
        //given
        TestDataItem testDataItem = testInitDB.dbInitWithSingleItem();
        Cake cake1 = (Cake) testDataItem.getItems().get("cake1");
        CakeImages cakeImages = cake1.getCakeImages();
        ImageFile profileImage = cakeImages.getProfileImage();
        //when
        ImageFile savedImageFile = imageStoreService.findById(profileImage.getId());
        //then
        Assertions.assertThat(cakeImages).isNotNull();

        Assertions.assertThat(profileImage).isNotNull();
        Assertions.assertThat(profileImage.getId()).isEqualTo(savedImageFile.getId());
        Assertions.assertThat(profileImage.getStoreFileName()).isEqualTo(savedImageFile.getStoreFileName());

        Assertions.assertThat(profileImage.getStoreFileName()).isNotEqualTo(profileImage.getUploadFileName());
    }






}