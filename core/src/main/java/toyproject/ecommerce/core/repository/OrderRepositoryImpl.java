package toyproject.ecommerce.core.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import toyproject.ecommerce.core.domain.Order;
import toyproject.ecommerce.core.domain.dto.OrderSearch;

import static toyproject.ecommerce.core.domain.QItem.item;
import static toyproject.ecommerce.core.domain.QOrder.order;
import static toyproject.ecommerce.core.domain.QOrderItem.orderItem;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Order> search(OrderSearch orderSearch, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(order.member.email.eq(orderSearch.getEmail()));
        if (orderSearch.getOrderStatus() != null) {
            builder.and(order.status.eq(orderSearch.getOrderStatus()));
        }

        QueryResults result = jpaQueryFactory
                .from(order)
                .innerJoin(order.orderItems, orderItem).fetchJoin()
                .innerJoin(orderItem.item, item)
                .where(builder)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(order.id.desc())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}
