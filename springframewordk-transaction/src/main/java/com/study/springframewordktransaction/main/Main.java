package com.study.springframewordktransaction.main;

import com.study.springframewordktransaction.config.ProjectConfig;
import com.study.springframewordktransaction.service.ProductService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext c = new AnnotationConfigApplicationContext(ProjectConfig.class)) {
            ProductService service = c.getBean(ProductService.class);
            service.addTenProducts(); // no transaction
        }
    }
}
