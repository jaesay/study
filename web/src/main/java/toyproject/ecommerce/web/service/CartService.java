package toyproject.ecommerce.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.ecommerce.core.domain.Cart;
import toyproject.ecommerce.core.domain.CartItem;
import toyproject.ecommerce.core.domain.Item;
import toyproject.ecommerce.core.domain.Member;
import toyproject.ecommerce.core.domain.exception.NotEnoughStockException;
import toyproject.ecommerce.core.repository.CartRepository;
import toyproject.ecommerce.core.repository.ItemRepository;
import toyproject.ecommerce.web.config.oauth.dto.SessionUser;
import toyproject.ecommerce.web.controller.dto.AddCartItemRequestDto;
import toyproject.ecommerce.web.controller.dto.AddCartItemResponseDto;
import toyproject.ecommerce.web.exception.ResourceNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long save(Member member) {
        Cart cart = cartRepository.findByMember_Id(member.getId())
                .orElse(Cart.createCart(member));

        if (cart.getId() == null) {
            cartRepository.save(cart);
        }
        return cart.getId();
    }

    public Cart getCart(SessionUser member) {
        Optional<Cart> cart = cartRepository.findByMember_Email(member.getEmail());

        return cart.get();
    }

    @Transactional
    public AddCartItemResponseDto saveCartItem(AddCartItemRequestDto requestDto, SessionUser member) {

        Item item = itemRepository.findById(requestDto.getItemId())
                .orElseThrow(ResourceNotFoundException::new);

        if (item.getStockQuantity() < requestDto.getItemCnt()) {
            throw new NotEnoughStockException(item.getName() + "'s stock is not enough.");
        }

        Cart cart = cartRepository.findByMember_Email(member.getEmail())
                .orElseThrow(ResourceNotFoundException::new);

        cart.addCartItem(item, requestDto.getItemCnt());
        cartRepository.save(cart);

        return AddCartItemResponseDto.builder()
                .cartId(cart.getId())
                .itemId(item.getId())
                .itemName(item.getName())
                .itemPrice(item.getPrice())
                .itemCnt(requestDto.getItemCnt())
                .build();
    }
}
