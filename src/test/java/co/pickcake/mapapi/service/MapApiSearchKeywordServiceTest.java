package co.pickcake.mapapi.service;

import co.pickcake.mapapi.distance.DistanceAlgorithm;
import co.pickcake.mapapi.request.ShopType;
import co.pickcake.mapapi.response.KaKaoMapApiResponse;

import co.pickcake.test.container.AbstractIntegrationContainerTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MapApiSearchKeywordServiceTest extends AbstractIntegrationContainerTest {

    @Autowired
    private MapApiSearchKeywordService mapApiSearchKeywordService;

    @Autowired
    private DistanceAlgorithm distanceAlgorithm;
    @Test
    void testGetDistance() {
//        given
//        when
//        then
        org.assertj.core.api.Assertions.assertThat(distanceAlgorithm).isNotNull();
        System.out.println("distanceAlgorithm = " + distanceAlgorithm);
    }

    @Test
    @DisplayName("api 테스트[success] 카카오 키워드 검색 ")
    void requestRecommendByKeyword() {
        // given
        // when
        KaKaoMapApiResponse response = mapApiSearchKeywordService.requestRecommendByKeywordAndType(37.5575055053737, 127.007952910656, 3D, "투썸", ShopType.CAFE);
        // then
        System.out.println("response = " + response);
        Assertions.assertThat(response.getMetaResponse().getTotalCount()).isGreaterThan(1);

    }
}