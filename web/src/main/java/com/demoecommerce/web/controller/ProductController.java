package com.demoecommerce.web.controller;

import com.demoecommerce.domain.entity.Product;
import com.demoecommerce.web.exception.ResourceNotFoundException;
import com.demoecommerce.web.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = {"/categories", "/categories/{categoryId}"})
    public String list(@PathVariable Optional<String> categoryId, Model model) {

        Page<Product> products = productService.getProducts(
                categoryId.isPresent() ? categoryId.get() : "",
                PageRequest.of(0, 9));

        model.addAttribute("products", products);

        return "/content/products/list";
    }

    @GetMapping("/{productId}")
    public String detail(@PathVariable Long productId, Model model) {

        Product product = productService.getProduct(productId)
                .orElseThrow(ResourceNotFoundException::new);

        model.addAttribute("product", product);

        return "/content/products/detail";
    }

}
