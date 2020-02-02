package toyproject.ecommerce.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import toyproject.ecommerce.core.domain.dto.ItemSearch;
import toyproject.ecommerce.core.domain.dto.ItemSummaryDto;
import toyproject.ecommerce.web.service.ItemService;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final ItemService itemService;

    @GetMapping("")
    public String index(@ModelAttribute("itemSearch") ItemSearch itemSearch, Model model) {
        Page<ItemSummaryDto> items = itemService.findItems(itemSearch, PageRequest.of(0, 9));

        model.addAttribute("items", items);

        return "index";
    }
}
