package co.pickcake.reservation.searchcake.service;

import co.pickcake.aop.util.exception.NoDataException;
import co.pickcake.common.entity.GeoCode;
import co.pickcake.mapapi.response.KaKaoDocumentResponse;
import co.pickcake.mapapi.response.KaKaoMapApiResponse;
import co.pickcake.mapapi.service.MapSearchApiService;
import co.pickcake.reservation.domain.item.Cake;
import co.pickcake.reservation.searchcake.repository.CakeRepository;
import co.pickcake.reservation.searchcake.response.CakeDetailSearchWithFeature;
import co.pickcake.shop.domain.Shop;
import co.pickcake.shop.repository.ShopJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class CakeSearchDetailService {

    private final CakeRepository cakeUserRepository;
    private final MapSearchApiService mapSearchApiService;
    private final ShopJpaRepository shopRepository;

    @Transactional
    public CakeDetailSearchWithFeature findBySingleDetailWithMap(Long cakeId) {
        Optional<Cake> byId = Optional.ofNullable(cakeUserRepository.findByIdDetails(cakeId));
        CakeDetailSearchWithFeature request = byId.map(CakeDetailSearchWithFeature::new).orElseThrow(NoDataException::new);

        if (request.getShop().getLatitude() == null | request.getShop().getLongitude() == null) {
            KaKaoMapApiResponse response = mapSearchApiService.searchGeoOnKAKAO(request.getShop().getAddress());
            KaKaoDocumentResponse first = response.getDocumentResponses().getFirst();
            GeoCode geoCode = GeoCode.create(first.getLatitude(), first.getLongitude());
            request.getShop().setLatitude(geoCode.getLatitude());
            request.getShop().setLongitude(geoCode.getLongitude());
            /* FIXME 변경 감지 일단 시도 */
            Optional<Shop> shop = shopRepository.findById(request.getShop().getShopId());
            shop.ifPresent(value -> value.setGeoCode(geoCode));
        }
        return request;
    }
}
