package co.pickcake.orderdomain.api;


import co.pickcake.orderdomain.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CakeApiController {

    private final ItemService itemService;

}
