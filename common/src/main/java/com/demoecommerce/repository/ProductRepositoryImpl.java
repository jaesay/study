package com.demoecommerce.repository;

import com.demoecommerce.domain.entity.Product;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static com.demoecommerce.domain.entity.QProduct.product;
import static com.demoecommerce.domain.entity.QProductSku.productSku;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Product> getProducts(String categoryId, Pageable pageable) {

        BooleanBuilder builder = new BooleanBuilder();

        if (!categoryId.isBlank()) {
            builder.and(product.category.categoryId.eq(categoryId));
        }

        QueryResults result = jpaQueryFactory
                .select(product)
                .from(product)
                .where(builder)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(product.productId.desc())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

}
