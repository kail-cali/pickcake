package co.pickcake.reservation.searchcake.controller;

import co.pickcake.reservation.searchcake.dto.CakeCategorySearch;
import co.pickcake.reservation.searchcake.dto.CakeDetailSearch;
import co.pickcake.reservation.searchcake.dto.CakeSimpleSearch;
import co.pickcake.reservation.searchcake.service.CakeSearchService;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


import java.util.List;

@Controller
@RequiredArgsConstructor
public class CakeSearchFormController {

    private final CakeSearchService cakeSearchService;
    @GetMapping("/cakes")
    public String viewList(Model model) {
        List<CakeSimpleSearch> response = cakeSearchService.findAll(0,10);
        model.addAttribute("items", response);
        model.addAttribute("categoryFilter", "전체");
        return "index";
    }
    @GetMapping("/cakes/category")
    public String viewCategoryList(
            @Validated @ModelAttribute(value="categoryName") @NotEmpty String categoryName
            ,Model model) {
        List<CakeCategorySearch> bySingleCategory = cakeSearchService.findBySingleCategory(0, 10, categoryName);
        model.addAttribute("items", bySingleCategory);
        model.addAttribute("categoryFilter", categoryName);
        return "item-list";

    }
    @GetMapping("/cakes/details")
    public String viewDetails(
            @Validated @ModelAttribute(value="itemId") Long itemId,
            Model model
    ) {
        CakeDetailSearch detail = cakeSearchService.findBySingleDetail(itemId);
        model.addAttribute("items", detail);
        return "item-details";

    }


}

