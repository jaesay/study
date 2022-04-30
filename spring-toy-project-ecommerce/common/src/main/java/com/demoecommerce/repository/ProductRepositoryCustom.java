package com.demoecommerce.repository;

import com.demoecommerce.domain.dto.ProductDto;
import com.demoecommerce.domain.dto.ProductSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductRepositoryCustom {
    Page<ProductSummaryDto> getProductsByCategory(String categoryId, Long productOptionId, Boolean forSale, Pageable pageable);

    Optional<ProductDto> getProduct(Long productId, Long productOptionId, Long productOptionValueId, Boolean forSale);
}
