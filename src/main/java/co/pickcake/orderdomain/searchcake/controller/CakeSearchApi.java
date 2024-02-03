package co.pickcake.orderdomain.searchcake.controller;


import co.pickcake.orderdomain.searchcake.dto.CakeCategorySearch;
import co.pickcake.orderdomain.searchcake.dto.CakeSimpleSearch;
import co.pickcake.orderdomain.searchcake.repository.CakeUserRepository;
import co.pickcake.orderdomain.searchcake.service.CakeSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CakeSearchApi {

    private final CakeSearchService cakeSearchService;

    private final CakeUserRepository cakeUserRepository;

    /* TODO validate & null search option 처리 */

    @GetMapping("/api/cake")
    public List<CakeSimpleSearch> searchAllCake(
            @RequestParam(value ="offset", defaultValue = "0") int offset,
            @RequestParam(value ="limit", defaultValue = "10") int limit) {
        return cakeSearchService.findAll(offset, limit);
    }

    @GetMapping("/api/cake/brand")
    public List<CakeSimpleSearch> searchByBrand(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "brand", defaultValue = "") String brand) {
        return cakeSearchService.findByBrand(offset, limit, brand);
    }

    @GetMapping("/api/cake/category")
    public List<CakeCategorySearch> searchBySingleCategory(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "categoryName", defaultValue = "") String categoryName) {

        return cakeSearchService.findBySingleCategory(offset,limit,categoryName);
    }

    /* like 로 아이템 이름 조회 */
    @GetMapping("/api/cake/name")
    public List<CakeSimpleSearch> searchByName(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "cakeName", defaultValue = "") String cakeName) {
        return cakeSearchService.findByNameOnLike(offset, limit, cakeName);
    }






}
