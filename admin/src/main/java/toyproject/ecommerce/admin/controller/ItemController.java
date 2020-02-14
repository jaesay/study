package toyproject.ecommerce.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import toyproject.ecommerce.admin.controller.dto.ItemForm;
import toyproject.ecommerce.admin.service.CategoryService;
import toyproject.ecommerce.admin.service.ItemService;
import toyproject.ecommerce.core.domain.Category;
import toyproject.ecommerce.core.domain.Item;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;
    private final CategoryService categoryService;

    @GetMapping("/items")
    public String itemList(@PageableDefault(value = 9) Pageable pageable, Model model) {

        Page<Item> items = itemService.findItems(pageable);
        model.addAttribute("items", items);

        return "itemList";
    }

    @GetMapping("/items/new")
    public String createForm(Model model) {

        List<Category> categories = categoryService.findChildCategories();

        model.addAttribute("itemForm", new ItemForm());
        model.addAttribute("categories", categories);

        return "createItemForm";
    }

    @PostMapping("/items/new")
    public String create(ItemForm form, BindingResult result) throws IOException {

        if (result.hasErrors()) {
            return "createItemForm";
        }

        Item item = form.toEntity();
        MultipartFile multipartFile = form.getImage();
        Long categoryId = form.getCategoryId();

        itemService.save(item, multipartFile, categoryId);

        return "redirect:/items";
    }
}
