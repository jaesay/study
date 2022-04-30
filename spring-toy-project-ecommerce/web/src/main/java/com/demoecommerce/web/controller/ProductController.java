package com.demoecommerce.web.controller;

import com.demoecommerce.domain.dto.ProductDto;
import com.demoecommerce.domain.dto.ProductOptionDto;
import com.demoecommerce.domain.dto.ProductSummaryDto;
import com.demoecommerce.web.exception.ResourceNotFoundException;
import com.demoecommerce.web.service.ProductOptionService;
import com.demoecommerce.web.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final ProductOptionService optionService;

    @GetMapping(value = {"/categories", "/categories/{categoryId}"})
    public String list(@PathVariable Optional<String> categoryId, Model model) {
        Page<ProductSummaryDto> products = productService.getProductsByCategory(
                categoryId.isPresent() ? categoryId.get() : "",
                1L, true,
                PageRequest.of(0, 9));

        model.addAttribute("products", products);

        return "/content/products/list";
    }

    @GetMapping("/{productId}/{productOptionValueId}")
    public String detail(@PathVariable Long productId, @PathVariable Long productOptionValueId, Model model) {

        ProductDto product = productService.getProduct(productId, productOptionValueId)
                .orElseThrow(ResourceNotFoundException::new);

        List<Long> productSkuIds = productService.getProductSkuIds(productId, productOptionValueId);

        List<ProductOptionDto> optionDtos = optionService.getProductOptions(2L, productSkuIds);

        model.addAttribute("product", product);
        model.addAttribute("options", optionDtos);

        return "/content/products/detail";
    }

}
