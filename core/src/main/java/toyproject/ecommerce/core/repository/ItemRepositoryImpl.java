package toyproject.ecommerce.core.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import toyproject.ecommerce.core.domain.dto.ItemSearch;
import toyproject.ecommerce.core.domain.dto.ItemSummaryDto;

import static toyproject.ecommerce.core.domain.QCategory.category;
import static toyproject.ecommerce.core.domain.QItem.item;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ItemSummaryDto> search(ItemSearch itemSearch, Pageable pageable) {

        BooleanBuilder builder = new BooleanBuilder();

        //Todo Java 11 이상 시 isBlank로 변경
        String itemName = itemSearch.getItemName();
        if (itemName != null && !itemName.trim().isEmpty()) {
            builder.and(item.name.contains(itemName));
        }

        QueryResults result = jpaQueryFactory
                .select(Projections.constructor(ItemSummaryDto.class, item.name, item.price, item.stockQuantity, item.picture, category.name))
                .from(item)
                .innerJoin(item.category, category)
                .where(builder)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(item.id.desc())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}
