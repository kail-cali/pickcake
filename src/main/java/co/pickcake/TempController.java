package co.pickcake;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempController {

    @GetMapping("main")
    public String getMain(Model model) {
//        model.addAttribute("data", "my name");
        return "main";
    }

}
