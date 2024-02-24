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
public class KaKaoUriBuilderService {

    /* TODO api url 관리 방법 고민, 지금은 서비스 규모가 크지 않아 static final 로 관리 */
    private static final String KAKAO_LOCAL_SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    public URI builderUrlByAddress(Address address) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_ADDRESS_URL);
        uriBuilder.queryParam("query", address.toSimpleString());
        URI uri = uriBuilder.build().encode().toUri();
        return uri;
    }
    public URI builderUrlByAddressForTest(Address address) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/map/o/v1");
        uriBuilder.queryParam("query", address.toSimpleString());
        URI uri = uriBuilder.build().encode().toUri();
        log.info("[KaKaoUriBuilderService builderByAddressSearch] address: {}, url: {}", address, uri);
        return uri;
    }
}
