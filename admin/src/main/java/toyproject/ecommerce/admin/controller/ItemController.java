package toyproject.ecommerce.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import toyproject.ecommerce.admin.service.ItemService;

@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items")
    public String itemList() {
        return "itemList";
    }
}
