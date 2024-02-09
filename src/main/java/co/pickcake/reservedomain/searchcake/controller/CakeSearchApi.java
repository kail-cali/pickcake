package co.pickcake.reservedomain.searchcake.controller;


import co.pickcake.aop.errors.RestErrorDto;
import co.pickcake.reservedomain.searchcake.dto.CakeCategorySearch;
import co.pickcake.reservedomain.searchcake.dto.CakeSimpleSearch;
import co.pickcake.reservedomain.searchcake.dto.CakeSimpleSearchRequest;
import co.pickcake.reservedomain.searchcake.service.CakeSearchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CakeSearchApi {

    private final CakeSearchService cakeSearchService;
    @GetMapping("/api/cake")
    public List<CakeSimpleSearch> searchAllCake(
            @Validated @RequestParam(value ="offset", defaultValue = "0") @PositiveOrZero int offset,
            @Validated @RequestParam(value ="limit", defaultValue = "10") int limit) {
        return cakeSearchService.findAll(offset, limit);
    }
    /* session 처리를 위해 미리 추가해둔 메서드 */
    @GetMapping("/api/cake/call")
    public List<CakeSimpleSearch> searchCakeCall(@RequestBody @Valid CakeSimpleSearchRequest request) {
        return cakeSearchService.findAll(request.getOffset(), request.getLimit());
    }

    @GetMapping("/api/cake/brand")
    public List<CakeSimpleSearch> searchByBrand(
            @Validated @RequestParam(value = "offset", defaultValue = "0") @PositiveOrZero int offset,
            @Validated @RequestParam(value = "limit", defaultValue = "10") @Range(min=1, max=20) int limit,
            @Validated @RequestParam(value = "brand", defaultValue = "") @NotBlank String brand) {
        return cakeSearchService.findByBrand(offset, limit, brand);
    }

    @GetMapping("/api/cake/category")
    public List<CakeCategorySearch> searchBySingleCategory(
            @Validated @RequestParam(value = "offset", defaultValue = "0") @Range(min=0) int offset,
            @Validated @RequestParam(value = "limit", defaultValue = "10") @Range(min=1, max=20) int limit,
            @Validated @RequestParam(value = "categoryName", defaultValue = "all") @NotBlank String categoryName) {

        return cakeSearchService.findBySingleCategory(offset,limit,categoryName);
    }

    /* like 로 아이템 이름 조회 */
    @GetMapping("/api/cake/name")
    public List<CakeSimpleSearch> searchByName(
            @Validated @RequestParam(value = "offset", defaultValue = "0") @PositiveOrZero int offset,
            @Validated @RequestParam(value = "limit", defaultValue = "10") int limit,
            @Validated @RequestParam(value = "cakeName", defaultValue = "") String cakeName) {
        return cakeSearchService.findByNameOnLike(offset, limit, cakeName);
    }

}
