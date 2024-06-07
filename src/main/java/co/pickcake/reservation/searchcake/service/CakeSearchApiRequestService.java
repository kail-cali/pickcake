package co.pickcake.reservation.searchcake.service;

import co.pickcake.apiutil.WebClientUtil;
import co.pickcake.reservation.searchcake.response.PickCakeApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import java.net.URI;


@Service
@RequiredArgsConstructor
public class CakeSearchApiRequestService {
    /* MSA 로 구현할 경우, 내부 서비스 에 대한 api 요청 서비스 */
    private final WebClientUtil webClientUtil;
    private final PickCakeUriBuilderService pickCakeUriBuilderService;

    public PickCakeApiResponse searchCakes(Integer offset, Integer limit) {
        URI uri = pickCakeUriBuilderService.builderUrlByDefault(offset, limit);
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        return webClientUtil.get(uri, PickCakeApiResponse.class, map);
    }
}
