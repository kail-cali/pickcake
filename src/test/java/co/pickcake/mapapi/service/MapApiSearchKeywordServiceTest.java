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


/* 키워드 서치 관련 API 실제 동작하는지 만 테스트
* 만약 해당 테스트가 동작하지 않는다면 외부 api의 현재 관리상태를 살펴볼 것 */
/* FIXME 여기 컨테이너 테스트 이전 부터 springBootTest 자체가 싱글톤 제대로 동작하지 않는 것 같은데
*   제대로 된 콜스택도 따로 없고 의심가는 정황만 있어서 확인 필요 */
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