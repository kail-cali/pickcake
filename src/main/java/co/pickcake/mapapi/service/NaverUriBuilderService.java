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
public class NaverUriBuilderService {
    private static final String NAVER_LOCAL_SEARCH_ADDRESS_URL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode";
    public URI builderUrlByAddress(Address address) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(NAVER_LOCAL_SEARCH_ADDRESS_URL);
        uriBuilder.queryParam("query", address.toSimpleString());
        URI uri = uriBuilder.build().encode().toUri();
        return uri;
    }
}
