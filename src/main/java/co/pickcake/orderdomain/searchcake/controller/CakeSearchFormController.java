package co.pickcake.orderdomain.searchcake.controller;

import co.pickcake.orderdomain.entity.item.Item;
import co.pickcake.orderdomain.searchcake.dto.CakeSimpleSearch;
import co.pickcake.orderdomain.searchcake.service.CakeSearchApiService;
import co.pickcake.orderdomain.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Controller
@RequiredArgsConstructor
public class CakeSearchFormController {


    private final CakeSearchApiService cakeSearchApiService;


    @GetMapping("/cakes")
    public String viewList(Model model) {
        List<CakeSimpleSearch> cakeSimpleSearchs= cakeSearchApiService.searchCakes(0,20);
        model.addAttribute("items", cakeSimpleSearchs);
        return "items/cakeList";
    }




}

