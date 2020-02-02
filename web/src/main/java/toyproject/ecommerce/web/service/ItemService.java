package toyproject.ecommerce.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import toyproject.ecommerce.core.domain.dto.ItemSearch;
import toyproject.ecommerce.core.domain.dto.ItemSummaryDto;
import toyproject.ecommerce.core.repository.ItemRepository;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public Page<ItemSummaryDto> findItems(ItemSearch itemSearch, PageRequest pageable) {
        return itemRepository.search(itemSearch, pageable);
    }
}
