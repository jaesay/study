package com.demoecommerce.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("")
    public String index() {
        return "index";
    }

    @GetMapping("test")
    public String test() {
        return "/content/orders/test";
    }

    @GetMapping("/popup/juso")
    public String test1() {
        return "/content/orders/juso_popup";
    }

}
