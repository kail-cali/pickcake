package co.pickcake.reservedomain.searchcake.controller;


import co.pickcake.aop.errors.RestErrorDto;
import co.pickcake.reservedomain.searchcake.dto.CakeCategorySearch;
import co.pickcake.reservedomain.searchcake.dto.CakeSimpleSearch;
import co.pickcake.reservedomain.searchcake.service.CakeSearchService;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CakeSearchApi {

    private final CakeSearchService cakeSearchService;

    /* TODO validate & null search option 처리 */
    @GetMapping("/api/cake")
    public List<CakeSimpleSearch> searchAllCake(
            @Validated @RequestParam(value ="offset", defaultValue = "0") int offset,
            @Validated @RequestParam(value ="limit", defaultValue = "10") int limit) {
        return cakeSearchService.findAll(offset, limit);
    }

    @GetMapping("/api/cake/brand")
    public List<CakeSimpleSearch> searchByBrand(
            @Validated @RequestParam(value = "offset", defaultValue = "0") int offset,
            @Validated @RequestParam(value = "limit", defaultValue = "10") int limit,
            @Validated @RequestParam(value = "brand", defaultValue = "") String brand) {
        return cakeSearchService.findByBrand(offset, limit, brand);
    }

    @GetMapping("/api/cake/category")
    public List<CakeCategorySearch> searchBySingleCategory(
            @Validated @RequestParam(value = "offset", defaultValue = "0") int offset,
            @Validated @RequestParam(value = "limit", defaultValue = "10") int limit,
            @Validated @RequestParam(value = "categoryName", defaultValue = "all") String categoryName) {

        return cakeSearchService.findBySingleCategory(offset,limit,categoryName);
    }

    /* like 로 아이템 이름 조회 */
    @GetMapping("/api/cake/name")
    public List<CakeSimpleSearch> searchByName(
            @Validated @RequestParam(value = "offset", defaultValue = "0") int offset,
            @Validated @RequestParam(value = "limit", defaultValue = "10") int limit,
            @Validated @RequestParam(value = "cakeName", defaultValue = "") String cakeName) {
        return cakeSearchService.findByNameOnLike(offset, limit, cakeName);
    }

}
