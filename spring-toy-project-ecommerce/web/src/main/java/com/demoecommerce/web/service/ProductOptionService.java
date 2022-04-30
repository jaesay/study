package com.demoecommerce.web.service;

import com.demoecommerce.domain.dto.ProductOptionDto;
import com.demoecommerce.repository.ProductOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductOptionService {

    private final ProductOptionRepository productOptionRepository;

    public List<ProductOptionDto> getProductOptions(Long productOptionId, List<Long> productSkuIds) {
        return productOptionRepository.findByProductOptionIdAndProductSkuIdIn(productOptionId, productSkuIds);
    }
}
