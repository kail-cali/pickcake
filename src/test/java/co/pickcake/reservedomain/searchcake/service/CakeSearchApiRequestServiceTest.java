package co.pickcake.reservedomain.searchcake.service;

import co.pickcake.aop.apigateway.ApiGatewayConfig;
import co.pickcake.policies.filename.policy.FileUuidGeneratePolicy;
import co.pickcake.reservedomain.searchcake.dto.CakeSimpleSearch;
import co.pickcake.reservedomain.searchcake.repository.CakeAdminRepository;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/* 외부 api 요청 테스트는 웹계층에서 테스트하면 되지만
* 내부 api 를 서비스 콜이 아닌 http 요청으로 할 경우, 테스트 방식에 대한 고민이 필요 */
@SpringBootTest
@Transactional
class CakeSearchApiRequestServiceTest {
    @Autowired
    private ApiGatewayConfig apiGatewayConfig;

    @Autowired
    private CakeSearchApiRequestService cakeSearchApiService;

    @Autowired
    private FileUuidGeneratePolicy fileNamePolicy;

    @Test
    @DisplayName("api call 테스트[success]: 케이크 상품 아이템이 api 를 통해 정상 조회되는 지 테스트")
    public void apiCallRequestForCakeSearchAll() throws Exception {
        // given
        Optional<AbstractThrowableAssert> result = Optional.of(assertThatThrownBy(() -> cakeSearchApiService.searchCakes(0, 10)));
        // when

        System.out.println("result = " + result);

        // then

    }

    @Test
    @DisplayName("데이터 검증 : 필터 조회 테스트, 브랜드로 상품이 조회 되는지 테스트 ")
    public void findByBrandValidate() {

    }


    @Test
    @DisplayName("데이터 검증: 필터 조회 테스트, 카테고리별 상품이 조회 되는지 테스트")
    public void findByCategory() {

    }

    /*  api 인증 관련 테스트   */
    @Test
    @DisplayName("인증 : api 조회 시 인증 절차에 따라 보호되는지 테스트")
    public void checkValidateToken() {

    }


    /* api 부하 테스트 */
    @Test
    @DisplayName("부하 테스트: api 조회 시 한번에 traffic이 몰릴 떄 부하 테스트")
    public void apiCallHeavy() {

    }


}