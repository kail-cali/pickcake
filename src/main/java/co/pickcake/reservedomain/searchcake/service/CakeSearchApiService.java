package co.pickcake.reservedomain.searchcake.service;


import co.pickcake.aop.apigateway.ApiGatewayConfig;
import co.pickcake.reservedomain.searchcake.dto.CakeSimpleSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CakeSearchApiService {

    private final ApiGatewayConfig apiGatewayConfig;


    public List<CakeSimpleSearch> searchCakes(int offset, int limit) {
        WebClient uploadApi = WebClient.builder()
                .baseUrl(apiGatewayConfig.getSearchCakeGateWay())
                .build();

        /*TODO non-block or async & 예외처리 추가 */
        return uploadApi.get()
                .uri(apiGatewayConfig.getSearchCakeApi())
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }

}
