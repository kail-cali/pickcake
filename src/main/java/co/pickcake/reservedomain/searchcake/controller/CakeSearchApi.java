package co.pickcake.reservedomain.searchcake.controller;

import co.pickcake.mapapi.response.KaKaoMapApiResponse;
import co.pickcake.mapapi.service.MapSearchApiService;
import co.pickcake.reservedomain.searchcake.dto.CakeCategorySearch;
import co.pickcake.reservedomain.searchcake.dto.CakeDetailSearch;
import co.pickcake.reservedomain.searchcake.dto.CakeSimpleSearch;

import co.pickcake.reservedomain.searchcake.service.CakeSearchService;

import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/* 해당 api 는 로그인 없이 조회할 수 있는 api */
@Slf4j
@RestController
@RequiredArgsConstructor
public class CakeSearchApi {

    private final CakeSearchService cakeSearchService;
    private final MapSearchApiService mapSearchApiService;
    @GetMapping("/api/cake")
    public List<CakeSimpleSearch> searchAllCake(
            @Validated @RequestParam(value ="offset", defaultValue = "0") @PositiveOrZero int offset,
            @Validated @RequestParam(value ="limit", defaultValue = "10") @Range(min = 0, max = 20) int limit) {
        return cakeSearchService.findAll(offset, limit);
    }
    @GetMapping("/api/cake/brand")
    public List<CakeSimpleSearch> searchByBrand(
            @Validated @RequestParam(value = "offset", defaultValue = "0") @PositiveOrZero int offset,
            @Validated @RequestParam(value = "limit", defaultValue = "10") @Range(min=1, max=20) int limit,
            @Validated @RequestParam(value = "brand", defaultValue = "all") @NotBlank String brand) {
        return cakeSearchService.findByBrand(offset, limit, brand);
    }
    @GetMapping("/api/cake/category")
    public List<CakeCategorySearch> searchBySingleCategory(
            @Validated @RequestParam(value = "offset", defaultValue = "0") @PositiveOrZero int offset,
            @Validated @RequestParam(value = "limit", defaultValue = "10") @Range(min=1, max=20) int limit,
            @Validated @RequestParam(value = "categoryName", defaultValue = "all") @NotBlank String categoryName) {
        return cakeSearchService.findBySingleCategory(offset,limit,categoryName);
    }
    /* like 로 아이템 이름 조회 */
    @GetMapping("/api/cake/name")
    public List<CakeSimpleSearch> searchByName(
            @Validated @RequestParam(value = "offset", defaultValue = "0") @PositiveOrZero int offset,
            @Validated @RequestParam(value = "limit", defaultValue = "10") @Range(min=1, max=20) int limit,
            @Validated @RequestParam(value = "cakeName", defaultValue = "케이크") @NotBlank String cakeName) {
        return cakeSearchService.findByNameOnLike(offset, limit, cakeName);
    }
    /* 캐스팅 및 변환이 필요한 경우 requestParam 은 검증이 제대로 동작하지 않는 문제가 있음 */
    @GetMapping("/api/cake/details")
    public CakeDetailSearch searchByItemDetails(
            @Validated @RequestParam(value= "itemId") @NotEmpty String itemId) {
        return cakeSearchService.findBySingleDetail(Long.valueOf(itemId));
    }

    @GetMapping("/api/cake/deatils/o")
    public CakeDetailSearch searchByItemDetailsWithAPi(
            @Validated @RequestParam(value= "itemId") @NotEmpty String itemId) {

        CakeDetailSearch reselt = cakeSearchService.findBySingleDetail(Long.valueOf(itemId));

        // get 3rd party api call
        KaKaoMapApiResponse response = mapSearchApiService.searchGeoWithWebClientAddOnKAKAO(reselt.getShop().getAddress());

        return null;
    }

}
