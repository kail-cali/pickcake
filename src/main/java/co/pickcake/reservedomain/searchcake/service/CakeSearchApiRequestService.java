package co.pickcake.reservedomain.searchcake.service;


import co.pickcake.aop.apigateway.ApiGatewayConfig;
import co.pickcake.apiutil.WebClientUtil;
import co.pickcake.reservedomain.searchcake.dto.CakeSimpleSearch;
import co.pickcake.reservedomain.searchcake.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CakeSearchApiRequestService {

    private final ApiGatewayConfig apiGatewayConfig;

//    private final WebClientUtil webClientUtil;
    /* api 요청의 경우, 요청에 대한 응답을 받은 이후 시점에서 예외처리를 해야하기 때문에 더 상세한 고민이 필요
    * 혹시라도 nob-block 을 쓰거나 async로 소켓 통신하게 되면 반드시 후속 처리를 헤야한다.
    * TODO 다음과 같은 api call 예외처리 추가 예정
    *  - session timeout
    *  - bad gateway
    *  - session is closed -> 금번 이슈에서 발생하였지만 요청 시 세션이 아닌 다른 fd 로 session close 혹은 commit 이 가는 이슈가 있었음
    *  - non-block 으로 아직 응답 요청을 받지 못한 체 컨트롤러에서 처리해야할 경우
    *
    * */
    public ResultDto searchCakes(int offset, int limit) {
        WebClient uploadApi = WebClient.builder()
                .baseUrl(apiGatewayConfig.getSearchCakeGateWay())
                .build();

        /*TODO non-block or async & 예외처리 추가 */
        return uploadApi.get()
                .uri(apiGatewayConfig.getSearchCakeApi())
                .retrieve()
                .bodyToMono(ResultDto.class)
                .block();
    }





}
