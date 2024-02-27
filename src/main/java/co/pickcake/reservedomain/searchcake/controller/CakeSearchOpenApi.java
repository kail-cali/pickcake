package co.pickcake.reservedomain.searchcake.controller;

import co.pickcake.reservedomain.searchcake.dto.CakeCategorySearch;
import co.pickcake.reservedomain.searchcake.dto.CakeSimpleSearch;
import co.pickcake.reservedomain.searchcake.dto.CakeSimpleSearchRequest;
import co.pickcake.reservedomain.searchcake.dto.ResultDto;
import co.pickcake.reservedomain.searchcake.response.PickCakeApiResponse;
import co.pickcake.reservedomain.searchcake.response.PickCakeDocumentResponse;
import co.pickcake.reservedomain.searchcake.response.PickCakeMetaResponse;
import co.pickcake.reservedomain.searchcake.service.CakeSearchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/openapi")
public class CakeSearchOpenApi {

    private final CakeSearchService cakeSearchService;
    @GetMapping("/cake")
    public PickCakeApiResponse searchAllCake(
            @Validated @RequestParam(value ="offset", defaultValue = "0") @PositiveOrZero int offset,
            @Validated @RequestParam(value ="limit", defaultValue = "10") int limit) {
        List<CakeSimpleSearch> all = cakeSearchService.findAll(offset, limit);
        PickCakeMetaResponse meta = PickCakeMetaResponse.create(all.size());
        List<PickCakeDocumentResponse> collect = all.stream().map(PickCakeDocumentResponse::new).collect(Collectors.toList());
        return PickCakeApiResponse.create(HttpStatus.OK, meta, collect);

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
