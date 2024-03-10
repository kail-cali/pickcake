package co.pickcake.reservedomain.searchcake.service;

import co.pickcake.fake.UnitTestMockWebController;
import co.pickcake.imagedomain.entity.ProfileImage;
import co.pickcake.policies.filename.policy.FileNamePolicy;
import co.pickcake.policies.filename.policy.FileUuidGeneratePolicy;
import co.pickcake.reservedomain.entity.item.Cake;
import co.pickcake.reservedomain.searchcake.cache.SearchCakeRedisService;
import co.pickcake.reservedomain.searchcake.dto.CakeProfileImageDto;
import co.pickcake.reservedomain.searchcake.dto.CakeSimpleSearch;
import co.pickcake.reservedomain.searchcake.repository.CakeSearchRepository;
import co.pickcake.reservedomain.searchcake.repository.CakeUserRepository;
import org.apache.commons.compress.utils.Lists;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(UnitTestMockWebController.class)
@AutoConfigureMockMvc
public class CakeSearchApiUnitTest {

    /* 비즈니스 로직 ( runtime 분기 특히) 테스트 */

    @Autowired private MockMvc mockMvc;
    @MockBean
    private FileNamePolicy fileNamePolicy;

    @MockBean
    private CakeSearchService cakeSearchService;

    @MockBean
    private CakeUserRepository cakeUserRepository;   // DAO
    @MockBean
    private CakeSearchRepository cakeSearchRepository;  //DAO
    @MockBean
    private SearchCakeRedisService searchCakeRedisService; // redis

    /* see - setup */
    private List<CakeSimpleSearch> responseExpected;

    @BeforeEach
    void setUp() {
        cakeSearchService = new CakeSearchService(cakeUserRepository, cakeSearchRepository, searchCakeRedisService);
        fileNamePolicy = new FileUuidGeneratePolicy();
    }

    @Test
    @DisplayName("redis unit test[success]: 상품 조회 시, 이미 저장된 redis가 있다면 redis 에 있는 내용 출력 ")
    void getFromCacheRedis() {
        //given
        CakeProfileImageDto profile1 = new CakeProfileImageDto();
        profile1.setStorePath("XXX-TEST-XXX.1.png");
        CakeSimpleSearch item1 = CakeSimpleSearch.builder()
                .itemId(1L)
                .brand("신라 호텔")
                .profile(profile1)
                .name("화이트 홀레데이 케이크")
                .price(1000000)
                .build();

        CakeProfileImageDto profile2 = new CakeProfileImageDto();
        profile1.setStorePath("XXX-TEST-XXX.2.png");
        CakeSimpleSearch item2 = CakeSimpleSearch.builder()
                .itemId(2L)
                .brand("신라 호텔")
                .profile(profile2)
                .name("생크림 케이크")
                .price(200000)
                .build();

        List<CakeSimpleSearch> responseExpected = new ArrayList<>();
        responseExpected.add(item1);
        responseExpected.add(item2);

        //when
        Mockito.when(searchCakeRedisService.findAll()).thenReturn(responseExpected);
        Mockito.when(cakeUserRepository.findAll(0, 10)).thenReturn(Lists.newArrayList());
        List<CakeSimpleSearch> responseActual = cakeSearchService.findAll(0, 10);
        // then
        Assertions.assertThat(responseActual.size()).isEqualTo(2);
    }
    @Test
    @DisplayName("redis unit test[success]: 상품 조회 시, 정보가 없다면 빈 리스트를 내려줄 수 있어야 한다. ")
    void getFromDaoNoData() {
        //given
        //when
        Mockito.when(searchCakeRedisService.findAll()).thenReturn(Lists.newArrayList());
        Mockito.when(cakeUserRepository.findAll(0, 10)).thenReturn(Lists.newArrayList());
        List<CakeSimpleSearch> responseActual = cakeSearchService.findAll(0, 10);
        //then
        Assertions.assertThat(responseActual.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("redis unit test[success]: 상품 조회 시, redis에 정보가 없다면 Dao 로 가져와야 한다. ")
    void getFromDao() {
        //given
        Cake cake1 = Cake.createCakeWithImage("초코 케이크", "신라 호텔", "우유 생크림이 들어간 케이크",
                150000, fileNamePolicy);
        ProfileImage profileImage1 = ProfileImage.createImageFile("s_x1.png", fileNamePolicy);
        cake1.getCakeImages().setProfileImage(profileImage1);
        List<Cake> responseExpected = new ArrayList<>();
        responseExpected.add(cake1);
        //when
        Mockito.when(searchCakeRedisService.findAll()).thenReturn(Lists.newArrayList());
        Mockito.when(cakeUserRepository.findAll(0, 10)).thenReturn(responseExpected);
        List<CakeSimpleSearch> responseActual = cakeSearchService.findAll(0, 10);
        //then
        Assertions.assertThat(responseActual.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("redis paging unit test[success]: 페이징이 적용되어야 한다.")
    void redisPagingTest() {
        //given
        CakeProfileImageDto profile1 = new CakeProfileImageDto();
        profile1.setStorePath("XXX-TEST-XXX.1.png");
        CakeSimpleSearch item1 = CakeSimpleSearch.builder()
                .itemId(1L)
                .brand("신라 호텔")
                .profile(profile1)
                .name("화이트 홀레데이 케이크")
                .price(1000000)
                .build();

        CakeProfileImageDto profile2 = new CakeProfileImageDto();
        profile1.setStorePath("XXX-TEST-XXX.2.png");
        CakeSimpleSearch item2 = CakeSimpleSearch.builder()
                .itemId(2L)
                .brand("신라 호텔")
                .profile(profile2)
                .name("생크림 케이크")
                .price(200000)
                .build();

        List<CakeSimpleSearch> responseExpected = new ArrayList<>();
        responseExpected.add(item1);
        responseExpected.add(item2);

        //when
        Mockito.when(searchCakeRedisService.findAll()).thenReturn(responseExpected);
        Mockito.when(cakeUserRepository.findAll(0, 20)).thenReturn(Lists.newArrayList());
        List<CakeSimpleSearch> responseActual1 = cakeSearchService.findAll(0, 1);
        // then
        Assertions.assertThat(responseActual1.size()).isEqualTo(1);
    }



}
