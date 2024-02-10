package co.pickcake;


import co.pickcake.authdomain.entity.Member;


import co.pickcake.imagedomain.entity.ImageFile;
import co.pickcake.imagedomain.service.ImageStoreService;
import co.pickcake.reservedomain.entity.item.Cake;
import co.pickcake.reservedomain.entity.item.CakeCategory;
import co.pickcake.reservedomain.entity.item.EventCakeCategory;

import co.pickcake.policies.filename.policy.FileUuidGeneratePolicy;
import jakarta.annotation.PostConstruct;
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

/*
*  현재 해당 버전은 운영을 위해 미리 데이터를 업데이트 하는 용도로 사용하고 있으며
*  deploy 이후 사용할 수 없도록 수정 예정 메인 수정 시 해당 부분과 TestDbInitService 도 수정 필요
*
* */

@Component
@RequiredArgsConstructor
@Transactional
public class InitDb {

    private final InitService initService;

    @Value("${service.active}")
    private String service;

    @PostConstruct
    public void init() {

        if (!service.equals("test")) {
            initService.dbInitWithImageItem();
            initService.dbInitWithMember();
        }
    }
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final ImageStoreService imageStoreService;
        private final FileUuidGeneratePolicy fileUuidGeneratePolicy;

        @Value("${file.from}")
        private String root;

        public void dbInitWithItem() {


            Cake cake1 = Cake.createCakeWithImage("초코 케이크", "성심당", "우유 생크림이 들어간 케이크",
                    150000, fileUuidGeneratePolicy);

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

            List<EventCakeCategory> cakeCategories = EventCakeCategory.addCakeCategories(cake1,
                                                                        cakeCategory1, cakeCategory2, cakeCategory3);
            em.persist(cake1);

        }

        public int dbInitWithImageItem() {

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

            return 4;

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


//        public int dbInitWithOrder() {
//            Member member = Member.createMember("userA", "서울", "연세로,", "31122");
//
//            em.persist(member);
//
//            Cake cake1 = Cake.createCakeWithImage("생크림 케이크", "신라호텔", "우유 생크림이 들어간 케이크",
//                    150000, fileUuidGeneratePolicy);
//
//            em.persist(cake1);
//
//            CakeCategory cakeCategory1 = CakeCategory.createCategory("생일");
//            em.persist(cakeCategory1);
//
//
//            EventCakeCategory eventCakeCategory1 = EventCakeCategory.addCakeCategory(cake1,cakeCategory1);
//
//            em.persist(eventCakeCategory1);
//
//            Cake cake2 = Cake.createCakeWithImage("딸기 가득 시루떡 케이크", "성심당", "초코 카스테라에 딸기가 가득 올라간 케이크",
//                    210000, fileUuidGeneratePolicy);
//            em.persist(cake2);
//
//            CakeCategory cakeCategory2 = CakeCategory.createCategory("12월 크리스마스");
//            em.persist(cakeCategory2);
//
//            EventCakeCategory eventCakeCategory2 = EventCakeCategory.addCakeCategory(cake2,cakeCategory2);
//            em.persist(eventCakeCategory2);
//
//            EventCakeCategory eventCakeCategory3 = EventCakeCategory.addCakeCategory(cake2,cakeCategory1);
//            em.persist(eventCakeCategory3);
//
//
//            /* order items */
//            OrderItem orderItem1 = OrderItem.createOrderItem(cake1, 1300000, 0);
//            OrderItem orderItem2 = OrderItem.createOrderItem(cake2, 210000, 0);
//
//            Delivery delivery = new Delivery();
//            delivery.setAddress(member.getAddress());
//
//            Order order = Order.createOrder(member, delivery, orderItem1);
//            em.persist(order);
//            return 4;
//        }


        public void dbInitWithMember() {
            Member member = Member.createMember("hail1", "cali", "hhh111" ,"서울", "연세로", "31122");

            em.persist(member);

        }

    }
}


