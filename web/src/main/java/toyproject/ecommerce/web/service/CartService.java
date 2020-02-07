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
        validateDuplicateCart(member);
        Cart cart = Cart.createCart(member);
        cartRepository.save(cart);
        return cart.getId();
    }

    private void validateDuplicateCart(Member member) {
        int countByMemberId = cartRepository.countByMember_Id(member.getId());
        if (countByMemberId > 0) {
            throw new IllegalStateException(member.getEmail() + "'s cart already exists");
        }
    }
}
