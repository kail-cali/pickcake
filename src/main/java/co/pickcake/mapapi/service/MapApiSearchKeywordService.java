package co.pickcake.mapapi.service;

import co.pickcake.apiutil.WebClientUtil;
import co.pickcake.mapapi.request.ShopType;
import co.pickcake.mapapi.response.KaKaoMapApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class MapApiSearchKeywordService {

    private final KaKaoUriBuilderService kaKaoUriBuilderService;
    private final WebClientUtil webClientUtil;
    private final WebClient webClient;
    private final RestTemplate restTemplate;

    @Value("${naver.rest.api.id}")
    private String naverRestApiId;
    @Value("${naver.rest.api.key}")
    private String naverRestApiKey;
    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;
    @Retryable(
            retryFor = RuntimeException.class,
            maxAttempts = 2,
            backoff = @Backoff(delay = 100)
    )
    public KaKaoMapApiResponse requestRecommendByKeywordAndType(Double latitude, Double longitude, Double radius, String keyword, ShopType type) {
        URI uri = kaKaoUriBuilderService.builderUrlByKeyWordAndType(longitude, latitude, radius, keyword, type);
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set("User-Agent", "hail-mac");//Set the header for each request
            request.getHeaders().set(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey);
            return execution.execute(request, body);
        });
        return restTemplate.getForEntity(uri, KaKaoMapApiResponse.class).getBody();
    }

}
