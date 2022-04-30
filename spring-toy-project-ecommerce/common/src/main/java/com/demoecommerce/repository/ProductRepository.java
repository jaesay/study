package com.demoecommerce.repository;

import com.demoecommerce.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

//    Page<Product> findAllByCategoryCategoryId(String categoryId, Pageable pageable);
}
