package co.pickcake.reservedomain.searchcake.controller;

import co.pickcake.reservedomain.searchcake.dto.CakeSearch;
import co.pickcake.reservedomain.searchcake.dto.CakeSimpleSearch;
import co.pickcake.reservedomain.searchcake.service.CakeSearchApiService;
import co.pickcake.reservedomain.searchcake.service.CakeSearchService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

@Controller
@RequiredArgsConstructor
public class CakeSearchFormController {

    private final CakeSearchApiService cakeSearchApiService;
    private final CakeSearchService cakeSearchService;

    @GetMapping("/cakes")
    public String viewList(Model model) {
//        List<CakeSimpleSearch> response= cakeSearchApiService.searchCakes(0,20);
        List<CakeSimpleSearch> response = cakeSearchService.findAll(0,10);
        model.addAttribute("items", response);
        return "items/cakeList";
    }

    @GetMapping("/cakes/{offset}/{limit}")
    public String viewList(
            @PathVariable("offset") int offset,
            @PathVariable("limit") int limit,
            Model model) {


        List<CakeSimpleSearch> response = cakeSearchService.findAll(offset, limit);
        model.addAttribute("items", response);
        return "items/cakeList";
    }

}

