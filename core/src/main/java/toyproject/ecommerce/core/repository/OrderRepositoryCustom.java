package toyproject.ecommerce.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toyproject.ecommerce.core.domain.entity.Order;
import toyproject.ecommerce.core.repository.dto.OrderSearch;

public interface OrderRepositoryCustom {
    Page<Order> search(OrderSearch orderSearch, Pageable pageable);
}
