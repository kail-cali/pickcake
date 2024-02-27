package co.pickcake.mapapi.service;

import co.pickcake.common.entity.Address;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@Slf4j
@Service
@RequiredArgsConstructor
public class KaKaoUriBuilderService implements BaseUriBuilder  {

    /* TODO api url 관리 방법 고민, 지금은 서비스 규모가 크지 않아 static final 로 관리 */
    private static final String KAKAO_LOCAL_SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";
    private static final String KAKAO_LOCAL_CATEGORY_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/category.json";
    private static final String KAKAO_LOCAL_KEYWORD_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/keyword.json";

    @Override
    public URI builderUrlByAddress(Address address) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_ADDRESS_URL);
        uriBuilder.queryParam("query", address.toSimpleString());
        return uriBuilder.build().encode().toUri();
    }
    @Override
    public URI builderUrlByKeyWord(Double longitude, Double latitude, Double radius, String keyword) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_KEYWORD_SEARCH_URL);
        uriBuilder.queryParam("query", keyword);
        uriBuilder.queryParam("x", longitude);
        uriBuilder.queryParam("y", latitude);
        uriBuilder.queryParam("radius", radius*1000);
        uriBuilder.queryParam("sort", "distance");

        return uriBuilder.build().encode().toUri();
    }


}
