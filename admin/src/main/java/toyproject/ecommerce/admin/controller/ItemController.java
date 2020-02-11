package toyproject.ecommerce.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import toyproject.ecommerce.admin.service.ItemService;
import toyproject.ecommerce.core.domain.Item;

@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items")
    public String itemList(@PageableDefault(value = 9) Pageable pageable, Model model) {

        Page<Item> items = itemService.findItems(pageable);
        model.addAttribute("items", items);

        return "itemList";
    }
}
