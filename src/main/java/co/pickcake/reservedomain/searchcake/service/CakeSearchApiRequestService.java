package co.pickcake.reservedomain.searchcake.service;


import co.pickcake.aop.apigateway.ApiGatewayConfig;
import co.pickcake.apiutil.WebClientUtil;
import co.pickcake.reservedomain.searchcake.dto.CakeSimpleSearch;
import co.pickcake.reservedomain.searchcake.dto.ResultDto;
import co.pickcake.reservedomain.searchcake.response.PickCakeApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CakeSearchApiRequestService {

    private final ApiGatewayConfig apiGatewayConfig;

    /* MSA 로 구현할 경우, 내부 서비스 에 대한 api 요청 서비스 */
    private final WebClientUtil webClientUtil;
    private final PickCakeUriBuilderService pickCakeUriBuilderService;


    public PickCakeApiResponse searchCakes(Integer offset, Integer limit) {
        URI uri = pickCakeUriBuilderService.builderUrlByDefault(offset, limit);
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        return webClientUtil.get(uri, PickCakeApiResponse.class, map);
    }










}
