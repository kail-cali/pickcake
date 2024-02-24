package co.pickcake.mapapi.service;

import co.pickcake.aop.util.exception.BadApiRequestException;
import co.pickcake.common.entity.Address;
import co.pickcake.mapapi.response.KaKaoMapApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import java.net.URI;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class MapSearchApiService {

    private final RestTemplate restTemplate;
    private final NaverUriBuilderService naverUriBuilderService;
    private final KaKaoUriBuilderService kaKaoUriBuilderService;
    private final WebClient webClient;

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
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK "+ kakaoRestApiKey);
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(uri, HttpMethod.GET, entity, KaKaoMapApiResponse.class).getBody();
    }
    /* RestTemplate with Interceptor */

    public KaKaoMapApiResponse v2kakao(Address address) {
        URI uri = kaKaoUriBuilderService.builderUrlByAddress(address);
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set("User-Agent", "eltabo");//Set the header for each request
            request.getHeaders().set(HttpHeaders.AUTHORIZATION, "KakaoAK "+ kakaoRestApiKey);
            return execution.execute(request, body);
        });
        return restTemplate.getForEntity(uri, KaKaoMapApiResponse.class).getBody();
    }



    public String requestAddressSearchWithTemplate(Address address) {
        if (ObjectUtils.isEmpty(address)) {
            throw new BadApiRequestException();
        }
        URI uri = naverUriBuilderService.builderUrlByAddress(address);

        //Add a ClientHttpRequestInterceptor to the RestTemplate
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set("User-Agent", "eltabo");//Set the header for each request
            request.getHeaders().set("X-NCP-APIGW-API-KEY-ID", naverRestApiId);
            request.getHeaders().set("X-NCP-APIGW-API-KEY", naverRestApiKey);
            return execution.execute(request, body);
        });

        ResponseEntity<Map> response = restTemplate.getForEntity(uri, Map.class);
        System.out.println("response need= " + response);

        return Optional.of(response.getBody()).toString();
    }

    public String r2(Address address) {
        URI uri = naverUriBuilderService.builderUrlByAddress(address);

        return webClient.get().uri(uri)
                .header("X-NCP-APIGW-API-KEY-ID", naverRestApiId)
                .header("X-NCP-APIGW-API-KEY", naverRestApiKey)
                .retrieve().bodyToMono(String.class)
                .block();

    }





    public KaKaoMapApiResponse requestAddressWithWebClient(Address address) {
        throw new BadApiRequestException();
    }

}
