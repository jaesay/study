package com.demoecommerce.web.service;

import com.demoecommerce.domain.entity.Product;
import com.demoecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<Product> getProducts(String categoryId, Pageable pageable) {
        return productRepository.getProducts(categoryId, pageable);
    }

    public Optional<Product> getProduct(Long productId) {
        return productRepository.findById(productId);
    }
}
