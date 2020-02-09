package toyproject.ecommerce.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(basePackages = "toyproject.ecommerce.web.controller")
public class BaseController {

    @ExceptionHandler(Exception.class)
    public String defaultErrorHandler(Exception e, Model model) {
        log.error(e.getMessage());
        model.addAttribute("message", e.getMessage());
        return "error";
    }

}
