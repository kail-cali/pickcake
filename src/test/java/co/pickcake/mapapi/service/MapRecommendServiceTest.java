package co.pickcake.mapapi.service;

import co.pickcake.common.entity.Address;
import co.pickcake.mapapi.request.ShopRecommendRequest;
import co.pickcake.mapapi.request.ShopType;
import co.pickcake.mapapi.response.ShopRecommendResponse;
import co.pickcake.test.container.AbstractIntegrationContainerTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;


/* 통합 api 연동 기능 테스트 */
@SpringBootTest
@AutoConfigureMockMvc
class MapRecommendServiceTest extends AbstractIntegrationContainerTest {

    @Autowired
    private MapRecommendService mapRecommendService;
    @Test
    @DisplayName("통합 api 기능 테스트 [success]: 현재 주소를 기반으로 카테고리와 키우드로 검색 시 가장 가까운 가게를 추천하는 기능")
    void recommendNearShopOnKAKAO() {
        //given
        Address address = Address.createAddress("서울", "송파구", "");
        ShopRecommendRequest request = ShopRecommendRequest.create(ShopType.CAFE, "투썸", address);
        //when
        List<ShopRecommendResponse> responses = mapRecommendService.recommendNearShopOnKAKAO(request);
        //then
        Assertions.assertThat(responses.size()).isGreaterThan(0);
        System.out.println("responses = " + responses);
    }
}