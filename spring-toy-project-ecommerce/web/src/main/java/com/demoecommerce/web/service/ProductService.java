package com.demoecommerce.web.service;

import com.demoecommerce.domain.dto.ProductDto;
import com.demoecommerce.domain.dto.ProductSummaryDto;
import com.demoecommerce.domain.entity.ProductSku;
import com.demoecommerce.repository.ProductRepository;
import com.demoecommerce.repository.ProductSkuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductSkuRepository productSkuRepository;

    public Page<ProductSummaryDto> getProductsByCategory(String categoryId, Long productOptionId, Boolean forSale, Pageable pageable) {
        return productRepository.getProductsByCategory(categoryId, productOptionId, forSale, pageable);
    }

    public Optional<ProductDto> getProduct(Long productId, Long productOptionValueId) {
        return productRepository.getProduct(productId, 1L, productOptionValueId, true);
    }

    public List<ProductSku> getProductSkusInProductAndColor(Long productId, Long productOptionValueId) {
        return productSkuRepository.findByProductIdAndProductOptionValueId(productId, productOptionValueId);
    }

    public List<Long> getProductSkuIds(Long productId, Long productOptionValueId) {
        List<ProductSku> productSkus =  productSkuRepository.findByProductIdAndProductOptionValueId(productId, productOptionValueId);

        return productSkus.stream().map(ps -> ps.getProductSkuId())
                .collect(Collectors.toList());
    }
}
