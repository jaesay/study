package com.demoecommerce.repository;

import com.demoecommerce.domain.dto.ProductDto;
import com.demoecommerce.domain.dto.ProductSummaryDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static com.demoecommerce.domain.entity.QCategory.category;
import static com.demoecommerce.domain.entity.QProduct.product;
import static com.demoecommerce.domain.entity.QProductOption.productOption;
import static com.demoecommerce.domain.entity.QProductOptionValue.productOptionValue;
import static com.demoecommerce.domain.entity.QProductSku.productSku;
import static com.demoecommerce.domain.entity.QProductSkuValue.productSkuValue;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ProductSummaryDto> getProductsByCategory(String categoryId, Long productOptionId, Boolean forSale, Pageable pageable) {

        BooleanBuilder builder = new BooleanBuilder();

        if (!categoryId.isBlank()) {
            builder.and(product.category.categoryId.eq(categoryId));
        }
        builder.and(productOption.productOptionId.eq(productOptionId));
        builder.and(product.forSale.eq(forSale));

        QueryResults result = jpaQueryFactory
                .selectDistinct(Projections.constructor(ProductSummaryDto.class, product.productId, product.productName, product.price, product.forSale, product.onSale,
                        category.categoryId, category.categoryName, productSku.icon, productSku.introduction, productOption.productOptionId, productOption.optionName,
                        productOptionValue.productOptionValueId, productOptionValue.optionValue))
                .from(product)
                .join(category).on(product.category.categoryId.eq(category.categoryId))
                .join(productSku).on(product.productId.eq(productSku.product.productId))
                .join(productSkuValue).on(productSku.productSkuId.eq(productSkuValue.productSku.productSkuId))
                .join(productOptionValue).on(productSkuValue.productOptionValue.productOptionValueId.eq(productOptionValue.productOptionValueId))
                .join(productOption).on(productOptionValue.productOption.productOptionId.eq(productOption.productOptionId))
                .where(builder)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(product.productId.desc())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public Optional<ProductDto> getProduct(Long productId, Long productOptionId, Long productOptionValueId, Boolean forSale) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(product.productId.eq(productId));
        builder.and(productOption.productOptionId.eq(productOptionId));
        builder.and(productOptionValue.productOptionValueId.eq(productOptionValueId));
        builder.and(product.forSale.eq(forSale));

        ProductDto result = jpaQueryFactory
                .selectDistinct(Projections.constructor(ProductDto.class, product.productId, product.productName, product.price, product.forSale, product.onSale, category.categoryId,
                        category.categoryName, productSku.introduction, productSku.description, productSku.icon, productSku.images, productOption.productOptionId, productOption.optionName,
                        productOptionValue.productOptionValueId, productOptionValue.optionValue))
                .from(product)
                .join(category).on(product.category.categoryId.eq(category.categoryId))
                .join(productSku).on(product.productId.eq(productSku.product.productId))
                .join(productSkuValue).on(productSku.productSkuId.eq(productSkuValue.productSku.productSkuId))
                .join(productOptionValue).on(productSkuValue.productOptionValue.productOptionValueId.eq(productOptionValue.productOptionValueId))
                .join(productOption).on(productOptionValue.productOption.productOptionId.eq(productOption.productOptionId))
                .where(builder)
                .orderBy(product.productId.desc())
                .fetchOne();

        return Optional.ofNullable(result);
    }

}
