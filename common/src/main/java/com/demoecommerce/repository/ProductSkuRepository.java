package com.demoecommerce.repository;

import com.demoecommerce.domain.entity.ProductSku;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSkuRepository extends JpaRepository<ProductSku, Long> {
}
