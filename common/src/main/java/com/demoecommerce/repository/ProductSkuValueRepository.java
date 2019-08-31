package com.demoecommerce.repository;

import com.demoecommerce.domain.entity.ProductSkuValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSkuValueRepository extends JpaRepository<ProductSkuValue, Long> {
}
