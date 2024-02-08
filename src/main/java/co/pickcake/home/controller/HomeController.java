package co.pickcake.home.controller;


import co.pickcake.reservedomain.searchcake.dto.CakeSimpleSearch;
import co.pickcake.reservedomain.searchcake.service.CakeSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CakeSearchService cakeSearchService;

    @RequestMapping("/")
    public String home(Model model) {
        List<CakeSimpleSearch> response = cakeSearchService.findAll(0,10);
        model.addAttribute("items", response);
        return "index";   // -> home-admin.html
    }

}
