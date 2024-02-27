package co.pickcake.mapapi.service;

import co.pickcake.apiutil.WebClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class MapApiSearchCategoryService {
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

    

}
