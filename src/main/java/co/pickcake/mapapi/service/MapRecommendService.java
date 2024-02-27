package co.pickcake.mapapi.service;

import co.pickcake.aop.util.exception.AuthAccessDeniedException;
import co.pickcake.aop.util.exception.NoDataException;
import co.pickcake.mapapi.distance.DistanceAlgorithm;
import co.pickcake.mapapi.request.ShopRecommendRequest;
import co.pickcake.mapapi.request.ShopType;
import co.pickcake.mapapi.response.KaKaoDocumentResponse;
import co.pickcake.mapapi.response.KaKaoMapApiResponse;
import co.pickcake.mapapi.response.ShopRecommendResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class MapRecommendService {

    private final DistanceAlgorithm distanceAlgorithm;
    private final MapSearchApiService mapSearchApiService;
    private final MapApiSearchKeywordService mapApiSearchKeywordService;
    public List<ShopRecommendResponse> recommendNearShopOnKAKAO(ShopRecommendRequest request) {
        KaKaoMapApiResponse response = mapSearchApiService.searchGeoOnKAKAO(request.getUserCurrentaddress());
        if (response.getMetaResponse().getTotalCount() <= 0) {
            throw new NoDataException();
        }
        if (request.getType().equals(ShopType.CAFE)) {
            KaKaoDocumentResponse first = response.getDocumentResponses().getFirst();
            KaKaoMapApiResponse recommendByKeyword = mapApiSearchKeywordService
                    .requestRecommendByKeywordAndType(first.getLatitude(), first.getLongitude(), 3D, request.getKeyword(), request.getType());

            return recommendByKeyword.getDocumentResponses().stream()
                    .map(ShopRecommendResponse::new)
                    .sorted(Comparator.comparing(ShopRecommendResponse::getDistance))
                    .collect(Collectors.toList());
        } else {
            throw new AuthAccessDeniedException(); // 케이스에 따라 처리할 내용들이 많아서 우선 하나씩 기능 개발 예정, 코드도 복잡해질 것 같아 리팩토링 고민
        }
    }

}
