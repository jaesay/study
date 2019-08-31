package com.demoecommerce.repository;

import com.demoecommerce.domain.dto.ProductDetailDto;
import com.demoecommerce.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    Page<Product> getProducts(String categoryId, Pageable pageable);
}
