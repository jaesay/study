package com.demoecommerce.repository;

import com.demoecommerce.domain.entity.ProductSku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductSkuRepository extends JpaRepository<ProductSku, Long> {
    @Query(value = "SELECT ps " +
            "FROM ProductSku AS ps " +
            "INNER JOIN ProductSkuValue AS psv ON ps.productSkuId = psv.productSku.productSkuId " +
            "WHERE ps.product.productId = :productId AND psv.productOptionValue.productOptionValueId = :productOptionValueId")
    List<ProductSku> findByProductIdAndProductOptionValueId(@Param("productId") Long productId, @Param("productOptionValueId") Long productOptionValueId);
}
