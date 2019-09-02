package com.demoecommerce.repository;

import com.demoecommerce.domain.dto.ProductOptionDto;
import com.demoecommerce.domain.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    @Query("SELECT new com.demoecommerce.domain.dto.ProductOptionDto(" +
            "ps.productSkuId, po.productOptionId, po.optionName, pov.productOptionValueId, pov.optionValue, ps.stock) " +
            "FROM ProductOption AS po " +
            "INNER JOIN ProductOptionValue AS pov ON po.productOptionId = pov.productOption.productOptionId " +
            "INNER JOIN ProductSkuValue AS psv ON pov.productOptionValueId = psv.productOptionValue.productOptionValueId " +
            "INNER JOIN ProductSku AS ps ON psv.productSku.productSkuId = ps.productSkuId " +
            "WHERE po.productOptionId = :productOptionId AND ps.productSkuId IN (:productSkuId)")
    List<ProductOptionDto> findByProductOptionIdAndProductSkuIdIn(@Param("productOptionId") Long productOptionId, @Param("productSkuId") List<Long> productSkuIds);
}
