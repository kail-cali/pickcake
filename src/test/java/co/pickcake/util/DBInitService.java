package co.pickcake.util;

import co.pickcake.auth.domain.Member;
import co.pickcake.config.FileSystemConfig;
import co.pickcake.config.WebSecurityConfig;
import co.pickcake.image.domain.ProfileImage;
import co.pickcake.image.service.ImageStoreService;

import co.pickcake.reservation.domain.item.Cake;
import co.pickcake.reservation.domain.item.CakeCategory;
import co.pickcake.reservation.domain.item.EventCakeCategory;
import co.pickcake.shop.domain.Shop;
import co.pickcake.testconfig.InitCreate;
import co.pickcake.testconfig.TestDataItem;
import co.pickcake.testconfig.TestDataSize;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DBInitService implements TestInit{
    /*
    * custom class for Test
    * */
    @Value("${file.from}")  // application.yml 에서 파일 설정 필요
    private String root;

    private final FileSystemConfig fileSystemConfig;
    private final EntityManager em;
    private final ImageStoreService imageStoreService;
    private final WebSecurityConfig securityConfig;
    @Override
    public void preinit(InitCreate initCreate) {

    }

    @Override
    public TestDataSize dbInitWithDynamic() {
        return null;
    }

    @Override
    public TestDataSize dbInitWithMember() {
        Member member = Member.createMember("hail-cali", "hail@gmail.com", "123456" ,"서울", "연세로", "31122");
        String encoded = securityConfig.passwordEncoder().encode(member.getPassword());
        member.setPassword(encoded);
        em.persist(member);
        return new TestDataSize(1,1,1L,1L);
    }

    @Override
    public TestDataSize dbInitWithItems() {
        Shop shop1 = Shop.createShop("신라호텔", "https://www.shillahotels.com/membership/inquires/aboutShilla/memSeoulHotel.do",
                "02-301-1111", "서울", "동호로 249", "04605",
                false, true, 2,
                LocalTime.of(10, 0), LocalTime.of(16, 0),
                "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EC%8B%A0%EB%9D%BC%ED%98%B8%ED%85%94", "https://www.instagram.com/shillahotels/"
        );
        Shop shop2 = Shop.createShop("포시즌스호텔", "www.fourseasons.com",
                "02-301-1111", "서울", "새문안로 97", "03183",
                false, true, 2,
                LocalTime.of(10, 0), LocalTime.of(16, 0),
                "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&ssc=tab.nx.all&query=%ED%8F%AC%EC%8B%9C%EC%A6%8C%EC%8A%A4%ED%98%B8%ED%85%94&oquery=%EC%8B%A0%EB%9D%BC%ED%98%B8%ED%85%94&tqi=iNmhhlqpts0ssM42V5RsssssstC-523492","https://www.instagram.com/fourseasons/"
        );
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
                150000, fileSystemConfig.fileNamePolicy());

        List<EventCakeCategory> cakeCategories = EventCakeCategory.addCakeCategories(cake1,
                cakeCategory6, cakeCategory3, cakeCategory5);
//        em.persist(cake1);

        // set cake profile
        ProfileImage profileImage1 = ProfileImage.createImageFile("s_x1.png", fileSystemConfig.fileNamePolicy());
        profileImage1.setProfileCakeImages(cake1.getCakeImages());
//        em.persist(profileImage1);
//        uploadDirect(profileImage1);

        Cake cake2 = Cake.createCakeWithImage("얼루어링 원터 케이크", "신라호텔",
                "빨간 장미 케이크",
                240000, fileSystemConfig.fileNamePolicy());
        EventCakeCategory.addCakeCategories(cake2,
                cakeCategory6, cakeCategory3, cakeCategory4, cakeCategory5);
//        em.persist(cake2);
        // set cake profile
        ProfileImage profileImage2 = ProfileImage.createImageFile("s_x2.png", fileSystemConfig.fileNamePolicy());


        profileImage2.setProfileCakeImages(cake2.getCakeImages());
//        em.persist(profileImage2);
//        uploadDirect(profileImage2);

        Cake cake3 = Cake.createCakeWithImage("레드 크리스마스 케이크", "포시즌스호텔",
                "오렌지 무스 초코 케이크",
                250000, fileSystemConfig.fileNamePolicy());

        EventCakeCategory.addCakeCategories(cake3,
                cakeCategory6, cakeCategory3, cakeCategory5);
//        em.persist(cake3);

        // set cake profile
        ProfileImage profileImage3 = ProfileImage.createImageFile("s_x3.png", fileSystemConfig.fileNamePolicy());


        profileImage3.setProfileCakeImages(cake3.getCakeImages());
//        em.persist(profileImage3);
//        uploadDirect(profileImage3);

        Cake cake4 = Cake.createCakeWithImage("화이트 크리스마스 케이크", "포시즌스호텔",
                "우유 생크림 케이크",
                189000, fileSystemConfig.fileNamePolicy());

        EventCakeCategory.addCakeCategories(cake4,
                cakeCategory6, cakeCategory5);
//        em.persist(cake4);

        // set cake profile
        ProfileImage profileImage4 = ProfileImage.createImageFile("s_x4.png", fileSystemConfig.fileNamePolicy());

        profileImage4.setProfileCakeImages(cake4.getCakeImages());
//        em.persist(profileImage4);
//        uploadDirect(profileImage4);
        shop1.addCake(cake1);
        shop1.addCake(cake2);
        shop2.addCake(cake3);
        shop2.addCake(cake4);
        em.persist(shop1);
        em.persist(shop2);

        HashMap<String, Object> items = new HashMap<>();
        System.out.println("items.get(\"cake\") = " + items.get("cake"));
        TestDataSize testDataSize = new TestDataSize(4,4,1L,1L);
        return testDataSize;
    }

    @Override
    public TestDataItem dbInitWithSingleItem() {

        Member member = Member.createMember("hail-cali", "hail@gmail.com", "123456" ,"서울", "연세로", "31122");
        member.setPassword(securityConfig.passwordEncoder().encode(member.getPassword()));
        em.persist(member);

        Shop shop1 = Shop.createShop("신라호텔", "https://www.shillahotels.com/membership/inquires/aboutShilla/memSeoulHotel.do",
                "02-301-1111", "서울", "동호로 249", "04605",
                false, true, 2,
                LocalTime.of(10, 0), LocalTime.of(16, 0),
                "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EC%8B%A0%EB%9D%BC%ED%98%B8%ED%85%94", "https://www.instagram.com/shillahotels/"
        );
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
                150000, fileSystemConfig.fileNamePolicy());

        List<EventCakeCategory> cakeCategories = EventCakeCategory.addCakeCategories(cake1,
                cakeCategory6, cakeCategory3, cakeCategory5);



        // set cake profile
        ProfileImage profileImage1 = ProfileImage.createImageFile("s_x1.png", fileSystemConfig.fileNamePolicy());

        profileImage1.setProfileCakeImages(cake1.getCakeImages());

//        uploadDirect(profileImage1);
        em.persist(cake1);
        em.persist(profileImage1);
        shop1.addCake(cake1);
        em.persist(shop1);

        HashMap<String, Object> items = new HashMap<>();
        items.put("cake1", cake1);
        items.put("shop1", shop1);
        items.put("member1", member);

        TestDataItem testDataItem = new TestDataItem(cake1.getId(), items);
        return testDataItem;
    }

    private void uploadDirect(ProfileImage profileImage) {
        File file = new File(root + profileImage.getUploadFileName());
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            String fullPath = imageStoreService.getFullPath(profileImage.getStoreFileName());
            fileInputStream.transferTo(new FileOutputStream(fullPath));
        } catch (IOException e) {

        }
    }

}
