package com.demoecommerce.repository;

import com.demoecommerce.domain.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    List<CartProduct> findByCartId(Long cartId);

    List<CartProduct> findByCartIdAndProductSku_ProductSkuIdIn(Long cartId, List<Long> productSkuIds);

    List<CartProduct> findByUpdatedDateBefore(LocalDateTime localDateTime);
}
