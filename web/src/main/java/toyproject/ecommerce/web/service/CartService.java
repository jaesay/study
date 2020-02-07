package toyproject.ecommerce.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.ecommerce.core.domain.Cart;
import toyproject.ecommerce.core.domain.Member;
import toyproject.ecommerce.core.repository.CartRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CartService {

    private final CartRepository cartRepository;

    @Transactional
    public Long save(Member member) {
        Cart cart = cartRepository.findByMember_Id(member.getId())
                .orElse(Cart.createCart(member));

        if (cart.getId() == null) {
            cartRepository.save(cart);
        }
        return cart.getId();
    }
}
