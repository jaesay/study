package toyproject.ecommerce.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toyproject.ecommerce.core.repository.dto.ItemSearch;
import toyproject.ecommerce.core.repository.dto.ItemSummaryDto;

public interface ItemRepositoryCustom {
    Page<ItemSummaryDto> search(ItemSearch itemSearch, Pageable pageable);
}
