package co.pickcake.orderdomain.searchcake.controller;


import co.pickcake.orderdomain.searchcake.dto.CakeCategorySearch;
import co.pickcake.orderdomain.searchcake.dto.CakeSimpleSearch;
import co.pickcake.orderdomain.searchcake.repository.CakeUserRepository;
import co.pickcake.orderdomain.searchcake.service.CakeSearchService;
import lombok.Getter;
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





}
