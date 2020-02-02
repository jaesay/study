package toyproject.ecommerce.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toyproject.ecommerce.core.domain.dto.ItemSearch;
import toyproject.ecommerce.core.domain.dto.ItemSummaryDto;

public interface ItemRepositoryCustom {
    Page<ItemSummaryDto> search(ItemSearch itemSearch, Pageable pageable);
}
