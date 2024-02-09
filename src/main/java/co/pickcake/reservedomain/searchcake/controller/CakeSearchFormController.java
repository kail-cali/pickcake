package co.pickcake.reservedomain.searchcake.controller;

import co.pickcake.reservedomain.searchcake.dto.CakeSimpleSearch;
import co.pickcake.reservedomain.searchcake.service.CakeSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Controller
@RequiredArgsConstructor
public class CakeSearchFormController {

    private final CakeSearchService cakeSearchService;
    @GetMapping("/cakes")
    public String viewList(Model model) {
        List<CakeSimpleSearch> response = cakeSearchService.findAll(0,10);
        model.addAttribute("items", response);
        return "index";
    }


}

