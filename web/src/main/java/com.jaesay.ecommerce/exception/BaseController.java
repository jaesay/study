package com.jaesay.ecommerce.exception;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BaseController {

    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(Exception exception, Model model) throws Exception {
        if (AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null)
            throw exception;

        model.addAttribute("message", "global error");
        return "/content/error/error";
    }
}
