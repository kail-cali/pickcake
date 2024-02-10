package co.pickcake.util;

import co.pickcake.authdomain.entity.Member;
import co.pickcake.imagedomain.entity.ImageFile;
import co.pickcake.imagedomain.service.ImageStoreService;
import co.pickcake.policies.filename.policy.FileUuidGeneratePolicy;
import co.pickcake.reservedomain.entity.item.Cake;
import co.pickcake.reservedomain.entity.item.CakeCategory;
import co.pickcake.reservedomain.entity.item.EventCakeCategory;
import co.pickcake.testconfig.InitCreate;
import co.pickcake.testconfig.TestDataItem;
import co.pickcake.testconfig.TestDataSize;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DBInitService implements TestInit{
    /*
    * custom class for Test
    * */
    @Value("{file.from}")  // application.yml 에서 파일 설정 필요
    private String root;

    private final EntityManager em;
    private final ImageStoreService imageStoreService;
    private final FileUuidGeneratePolicy fileUuidGeneratePolicy;

    @Override
    public void preinit(InitCreate initCreate) {

    }

    @Override
    public TestDataSize dbInitWithDynamic() {
        return null;
    }

    @Override
    public TestDataSize dbInitWithMember() {
        Member member = Member.createMember("hail1", "cali", "hhh111" ,"서울", "연세로", "31122");

        em.persist(member);

        return new TestDataSize(1,1,1L,1L);
    }

    @Override
    public TestDataSize dbInitWithItems() {
        CakeCategory cakeCategory1 = CakeCategory.createCategory("가정의 달");
        CakeCategory cakeCategory2 = CakeCategory.createCategory("23 발렌타인 데이");
        CakeCategory cakeCategory3 = CakeCategory.createCategory("기념일");
        CakeCategory cakeCategory4 = CakeCategory.createCategory("생일");
        CakeCategory cakeCategory5 = CakeCategory.createCategory("파티");
        CakeCategory cakeCategory6 = CakeCategory.createCategory("23 시즌 크리스마스");

        em.persist(cakeCategory1);
        em.persist(cakeCategory2);
        em.persist(cakeCategory3);
        em.persist(cakeCategory4);
        em.persist(cakeCategory5);
        em.persist(cakeCategory6);

        Cake cake1 = Cake.createCakeWithImage("화이트 홀리데이 케이크", "신라호텔",
                "화이트 크리스마스 케이크",
                150000, fileUuidGeneratePolicy);

        List<EventCakeCategory> cakeCategories = EventCakeCategory.addCakeCategories(cake1,
                cakeCategory6, cakeCategory3, cakeCategory5);
        em.persist(cake1);

        // set cake profile
        ImageFile imageFile1 = ImageFile.createImageFile("s_x1.png", fileUuidGeneratePolicy);


        imageFile1.setProfileCakeImages(cake1.getCakeImages());
        em.persist(imageFile1);
        uploadDirect(imageFile1);

        Cake cake2 = Cake.createCakeWithImage("얼루어링 원터 케이크", "신라호텔",
                "빨간 장미 케이크",
                240000, fileUuidGeneratePolicy);

        EventCakeCategory.addCakeCategories(cake2,
                cakeCategory6, cakeCategory3, cakeCategory4, cakeCategory5);
        em.persist(cake2);

        // set cake profile
        ImageFile imageFile2 = ImageFile.createImageFile("s_x2.png", fileUuidGeneratePolicy);


        imageFile2.setProfileCakeImages(cake2.getCakeImages());
        em.persist(imageFile2);
        uploadDirect(imageFile2);

        Cake cake3 = Cake.createCakeWithImage("레드 크리스마스 케이크", "포시즌스 호텔",
                "오렌지 무스 초코 케이크",
                250000, fileUuidGeneratePolicy);

        EventCakeCategory.addCakeCategories(cake3,
                cakeCategory6, cakeCategory3, cakeCategory5);
        em.persist(cake3);

        // set cake profile
        ImageFile imageFile3 = ImageFile.createImageFile("s_x3.png", fileUuidGeneratePolicy);


        imageFile3.setProfileCakeImages(cake3.getCakeImages());
        em.persist(imageFile3);
        uploadDirect(imageFile3);

        Cake cake4 = Cake.createCakeWithImage("화이트 크리스마스 케이크", "포시즌스 호텔",
                "우유 생크림 케이크",
                189000, fileUuidGeneratePolicy);

        EventCakeCategory.addCakeCategories(cake4,
                cakeCategory6, cakeCategory5);
        em.persist(cake4);

        // set cake profile
        ImageFile imageFile4 = ImageFile.createImageFile("s_x4.png", fileUuidGeneratePolicy);


        imageFile4.setProfileCakeImages(cake4.getCakeImages());
        em.persist(imageFile4);
        uploadDirect(imageFile4);



        TestDataSize testDataSize = new TestDataSize(4,4,1L,1L);

        return testDataSize;
    }

    @Override
    public TestDataItem dbInitWithSingleItem() {
        CakeCategory cakeCategory1 = CakeCategory.createCategory("가정의 달");
        CakeCategory cakeCategory2 = CakeCategory.createCategory("23 발렌타인 데이");
        CakeCategory cakeCategory3 = CakeCategory.createCategory("기념일");
        CakeCategory cakeCategory4 = CakeCategory.createCategory("생일");
        CakeCategory cakeCategory5 = CakeCategory.createCategory("파티");
        CakeCategory cakeCategory6 = CakeCategory.createCategory("23 시즌 크리스마스");

        em.persist(cakeCategory1);
        em.persist(cakeCategory2);
        em.persist(cakeCategory3);
        em.persist(cakeCategory4);
        em.persist(cakeCategory5);
        em.persist(cakeCategory6);

        Cake cake1 = Cake.createCakeWithImage("화이트 홀리데이 케이크", "신라호텔",
                "화이트 크리스마스 케이크",
                150000, fileUuidGeneratePolicy);

        List<EventCakeCategory> cakeCategories = EventCakeCategory.addCakeCategories(cake1,
                cakeCategory6, cakeCategory3, cakeCategory5);
        em.persist(cake1);

        // set cake profile
        ImageFile imageFile1 = ImageFile.createImageFile("s_x1.png", fileUuidGeneratePolicy);


        imageFile1.setProfileCakeImages(cake1.getCakeImages());
        em.persist(imageFile1);
        uploadDirect(imageFile1);

        return new TestDataItem(cake1.getId(), cake1);
    }

    private void uploadDirect(ImageFile imageFile) {
        File file = new File(root + imageFile.getUploadFileName());
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            String fullPath = imageStoreService.getFullPath(imageFile.getStoreFileName());
            fileInputStream.transferTo(new FileOutputStream(fullPath));
        } catch (IOException e) {

        }
    }

}
