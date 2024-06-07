package co.pickcake.reservation.searchcake.service;

import co.pickcake.aop.apigateway.ApiGatewayConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@Slf4j
@Service
@RequiredArgsConstructor
public class PickCakeUriBuilderService {
    private final ApiGatewayConfig apiGatewayConfig;

    public URI builderUrlByDefault(Integer offset, Integer limit) {
        String url = apiGatewayConfig.getSearchCakeGateWay() + apiGatewayConfig.getSearchCakeApi();
        log.info("[pickcake-api uri builder] : {}", url);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        uriBuilder.queryParam("offset", offset);
        uriBuilder.queryParam("limit", limit);
        return  uriBuilder.build().encode().toUri();
    }



}
