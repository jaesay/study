package toyproject.ecommerce.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import toyproject.ecommerce.core.domain.entity.Cart;
import toyproject.ecommerce.core.domain.entity.CartItem;
import toyproject.ecommerce.core.repository.dto.ItemSearch;
import toyproject.ecommerce.core.repository.dto.ItemSummaryDto;
import toyproject.ecommerce.core.repository.ItemRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public Page<ItemSummaryDto> findItems(ItemSearch itemSearch, Cart cart, Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize());
        Page<ItemSummaryDto> searchItems = itemRepository.search(itemSearch, pageable);
        List<CartItem> cartItems = cart.getCartItems();

        if (searchItems.getTotalElements() > 0 && cartItems.size() > 0) {
            searchItems.forEach(itemSummaryDto -> itemSummaryDto.checkCartItem(cartItems));
        }

        return searchItems;
    }

}
