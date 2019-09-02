package com.demoecommerce.domain.entity;

import com.demoecommerce.domain.dto.ProductSummaryDto;
import com.demoecommerce.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProductTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void test() {
        Page<ProductSummaryDto> products = productRepository.getProductsByCategory("F101", 1L, true, PageRequest.of(0, 10));
        products.forEach(p -> System.out.println(p.getProductId()));
    }
}