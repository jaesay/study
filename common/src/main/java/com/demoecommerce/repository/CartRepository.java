package com.demoecommerce.repository;

import com.demoecommerce.domain.entity.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @EntityGraph(attributePaths = "cartProducts")
    Optional<Cart> findByAccountId(Long accountId);
}
