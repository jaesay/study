package com.web.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

    @GetMapping("/test")
    public void test() {}

    @GetMapping("/guest")
    @ResponseBody
    public String guest() {
        return "guest";
    }

    @ResponseBody
    @GetMapping("/manager")
    public String manager() {
        return "manager";
    }

    @ResponseBody
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/admin-secret")
    public String adminSecret() {
        return "adminSecret";
    }

    @ResponseBody
    @Secured("ROLE_MANAGER")
    @GetMapping("/manager-secret")
    public String managerSecret() {
        return "managerSecret";
    }


}
