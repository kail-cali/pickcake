package co.pickcake.mapapi.service;

import co.pickcake.aop.util.exception.BadApiRequestException;
import co.pickcake.common.entity.Address;
import co.pickcake.mapapi.response.KaKaoMapApiResponse;
import co.pickcake.mapapi.response.NaverMapApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import java.net.URI;
import java.util.Collections;

import static java.nio.charset.StandardCharsets.UTF_8;


@Slf4j
@Service
@RequiredArgsConstructor
public class MapSearchApiService {

    /* 해당 서비스는 강의용으로 활용할 수 있도록 리팩토링까지 추가로 진행하였는데,
    * 이 서비스 api를 참고해서 블로그나 강의에 가져가신다면 @Hail-cali 개발자 이름은 남겨시주길 바랍니다. */

    private final RestTemplate restTemplate;
    private final NaverUriBuilderService naverUriBuilderService;
    private final KaKaoUriBuilderService kaKaoUriBuilderService;
    private final WebClient webClient; // config 따로 만들어서 주입받아 사용하고 있습니다.

    @Value("${naver.rest.api.id}")
    private String naverRestApiId;

    @Value("${naver.rest.api.key}")
    private String naverRestApiKey;

    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;

    /* RestTemplate */
    public KaKaoMapApiResponse v1kakao(Address address) {
        if (ObjectUtils.isEmpty(address)) {
            throw new BadApiRequestException();
        }
        URI uri = kaKaoUriBuilderService.builderUrlByAddress(address);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey);
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(uri, HttpMethod.GET, entity, KaKaoMapApiResponse.class).getBody();
    }
    /* RestTemplate with Interceptor */

    public KaKaoMapApiResponse v2kakao(Address address) {
        URI uri = kaKaoUriBuilderService.builderUrlByAddress(address);
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set("User-Agent", "hail-mac");//Set the header for each request
            request.getHeaders().set(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey);
            return execution.execute(request, body);
        });
        return restTemplate.getForEntity(uri, KaKaoMapApiResponse.class).getBody();
    }
    public NaverMapApiResponse searchGeoWithRestTemplateNaverTune(Address address) {
        if (ObjectUtils.isEmpty(address)) {
            throw new BadApiRequestException();
        }
        URI uri = naverUriBuilderService.builderUrlByAddress(address);

        //Add a ClientHttpRequestInterceptor to the RestTemplate
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set("User-Agent", "hail-mac");//Set the header for each request
            request.getHeaders().set("X-NCP-APIGW-API-KEY-ID", naverRestApiId);
            request.getHeaders().set("X-NCP-APIGW-API-KEY", naverRestApiKey);
            return execution.execute(request, body);
        });
        return restTemplate.getForEntity(uri, NaverMapApiResponse.class).getBody();
    }



    /* WebClient :: IMPORTANT 외부 api 제공 및 내부에서 연동 api 로 사용하기 위해 따로 생성하였으며 추후 분리 예정 */
    public KaKaoMapApiResponse searchGeoWithWebClientKakaoNative(Address address) {
        URI uri = kaKaoUriBuilderService.builderUrlByAddress(address);
        return webClient.get().uri(uri)
                .header(HttpHeaders.AUTHORIZATION,"KakaoAK " + kakaoRestApiKey)
                .retrieve().bodyToMono(KaKaoMapApiResponse.class)
                .block();
    }
    public KaKaoMapApiResponse searchGeoWithWebClientKakaoTune(Address address) {
        URI uri = kaKaoUriBuilderService.builderUrlByAddress(address);
        return webClient.get().uri(uri)
                .headers(request -> {
                    request.setContentType(MediaType.APPLICATION_JSON);
                    request.setAcceptCharset(Collections.singletonList(UTF_8));
                    request.set(HttpHeaders.AUTHORIZATION,"KakaoAK " + kakaoRestApiKey);
                })
                .retrieve().bodyToMono(KaKaoMapApiResponse.class)
                .block();
    }

    public NaverMapApiResponse searchGeoWithWebClientNaverNative(Address address) {
        URI uri = naverUriBuilderService.builderUrlByAddress(address);

        return webClient.get().uri(uri)
                .header("X-NCP-APIGW-API-KEY-ID", naverRestApiId)
                .header("X-NCP-APIGW-API-KEY", naverRestApiKey)
                .retrieve().bodyToMono(NaverMapApiResponse.class)
                .block();
    }





    public KaKaoMapApiResponse requestAddressWithWebClient(Address address) {
        throw new BadApiRequestException();
    }

}
