package com.jaesay.ecommerce.exception;

import com.jaesay.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@RequiredArgsConstructor
public class BaseController {

    private final CategoryService categoryService;

    @ModelAttribute
    public void categories(Model model) {
        model.addAttribute("categories", categoryService.getCategories());
    }

    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(Exception exception, Model model) throws Exception {
        if (AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null)
            throw exception;

        model.addAttribute("message", "global error");
        return "/content/error/error";
    }
}
