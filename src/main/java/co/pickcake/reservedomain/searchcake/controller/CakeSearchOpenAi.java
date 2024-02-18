package co.pickcake.reservedomain.searchcake.controller;

import co.pickcake.reservedomain.searchcake.dto.CakeCategorySearch;
import co.pickcake.reservedomain.searchcake.dto.CakeSimpleSearch;
import co.pickcake.reservedomain.searchcake.dto.CakeSimpleSearchRequest;
import co.pickcake.reservedomain.searchcake.dto.ResultDto;
import co.pickcake.reservedomain.searchcake.service.CakeSearchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/openapi")
public class CakeSearchOpenAi {

    private final CakeSearchService cakeSearchService;
    @GetMapping("/cake")
    public ResultDto searchAllCake(
            @Validated @RequestParam(value ="offset", defaultValue = "0") @PositiveOrZero int offset,
            @Validated @RequestParam(value ="limit", defaultValue = "10") int limit) {
        List<CakeSimpleSearch> all = cakeSearchService.findAll(offset, limit);
        return new ResultDto(all.size(), all);
    }
    @GetMapping("/cake/call")
    public ResultDto searchCakeCall(@RequestBody @Valid CakeSimpleSearchRequest request) {
        List<CakeSimpleSearch> all = cakeSearchService.findAll(request.getOffset(), request.getLimit());
        return new ResultDto(all.size(), all);
    }
    @GetMapping("/cake/category/call")
    public ResultDto searchBySingleCategoryCall(
            @Valid @RequestBody CakeSimpleSearchRequest request) {
        List<CakeCategorySearch> bySingleCategory = cakeSearchService.findBySingleCategory(request.getOffset(), request.getLimit(), request.getCategoryName());
        return new ResultDto(bySingleCategory.size(), bySingleCategory);
    }
}
