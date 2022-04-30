package toyproject.ecommerce.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import toyproject.ecommerce.web.service.CategoryService;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice(basePackages = "toyproject.ecommerce.web.controller")
public class BaseController {

    private final CategoryService categoryService;

    @ExceptionHandler(Exception.class)
    public String defaultErrorHandler(Exception e, Model model) {
        log.error(e.getMessage());
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ModelAttribute
    public void categories(Model model) {
        model.addAttribute("categories", categoryService.getShopCategories());
    }

}
