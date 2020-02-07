package toyproject.ecommerce.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toyproject.ecommerce.core.domain.Cart;
import toyproject.ecommerce.core.domain.Member;
import toyproject.ecommerce.core.repository.CartRepository;
import toyproject.ecommerce.web.config.oauth.dto.SessionUser;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SessionService {

    private final CartRepository cartRepository;

    public SessionUser createSessionUser(Member member) {
        Cart cart = cartRepository.findByMember_Id(member.getId())
                .orElseThrow(() -> new IllegalStateException());

        return new SessionUser(member, cart.getId());
    }
}
