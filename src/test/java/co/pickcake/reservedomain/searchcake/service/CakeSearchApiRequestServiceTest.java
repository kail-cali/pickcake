package co.pickcake.reservedomain.searchcake.service;

import co.pickcake.aop.apigateway.ApiGatewayConfig;
import co.pickcake.policies.filename.policy.FileUuidGeneratePolicy;
import co.pickcake.reservedomain.searchcake.dto.CakeSimpleSearch;
import co.pickcake.reservedomain.searchcake.repository.CakeAdminRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class CakeSearchApiRequestServiceTest {
    @Autowired
    private ApiGatewayConfig apiGatewayConfig;

    @Autowired
    private CakeSearchApiRequestService cakeSearchApiService;



    @Autowired
    private CakeAdminRepository cakeAdminRepository;

    @Autowired
    private FileUuidGeneratePolicy fileNamePolicy;

    @Test
    @DisplayName("데이터 검증 : 케이크 상품 아이템이 api 를 통해 정상 조회되는 지 테스트")
    public void findAllValidate() {



        //when


        List<CakeSimpleSearch> cakeSimpleSearches = cakeSearchApiService.searchCakes(0, 10);


        // 상품 갯수가 정상적으로 조회되는지
//        assertThat(cakeSimpleSearches.size()).isEqualTo(2);
        System.out.println("cakeSimpleSearches = " + cakeSimpleSearches);
        System.out.println("cakeSimpleSearches = " + cakeSimpleSearches.size());
//        // 상품 dto 가 잘 가져와지는 지 테스트
//        CakeSimpleSearch first = cakeSimpleSearches.getFirst();
//        assertThat(first.getBrand()).isEqualTo("신라 호텔");
//        assertThat(first.getName()).isEqualTo("초코 케이크");
//        assertThat(first.getPrice()).isEqualTo(150000);
//        assertThat(first.getProfile()).isInstanceOf(CakeProfileImageDto.class);
//        assertThat(first.getProfile().getStorePath()).isNotEmpty();
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