package co.pickcake.image.service;

import co.pickcake.image.domain.CakeImages;
import co.pickcake.image.domain.ProfileImage;
import co.pickcake.image.repository.CakeImageRepository;
import co.pickcake.reservation.domain.item.Cake;
import co.pickcake.test.container.AbstractIntegrationContainerTest;
import co.pickcake.testconfig.TestDataItem;
import co.pickcake.util.TestInitDB;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class ImageStoreServiceTest extends AbstractIntegrationContainerTest {


    @Autowired private ImageStoreService imageStoreService;
    @Autowired private CakeImageRepository cakeImageRepository;
    @Autowired private TestInitDB testInitDB;

    @Test
    @DisplayName("기능 테스트[success]: 이미지 메타데이터가 정상적으로 저장되는 지 확인")
    @Transactional
    public void saveImageFile_v1() {
        //given
        TestDataItem testDataItem = testInitDB.dbInitWithSingleItem();
        Cake cake1 = (Cake) testDataItem.getItems().get("cake1");
        CakeImages cakeImages = cake1.getCakeImages();
        ProfileImage profileImage = cakeImages.getProfileImage();
        //when
        ProfileImage savedProfileImage = imageStoreService.findById(profileImage.getId());
        //then
        Assertions.assertThat(cakeImages).isNotNull();

        Assertions.assertThat(profileImage).isNotNull();
        Assertions.assertThat(profileImage.getId()).isEqualTo(savedProfileImage.getId());
        Assertions.assertThat(profileImage.getStoreFileName()).isEqualTo(savedProfileImage.getStoreFileName());

        Assertions.assertThat(profileImage.getStoreFileName()).isNotEqualTo(profileImage.getUploadFileName());
    }






}