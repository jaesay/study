package toyproject.ecommerce.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toyproject.ecommerce.core.domain.Member;
import toyproject.ecommerce.core.repository.CartRepository;
import toyproject.ecommerce.web.config.oauth.dto.SessionUser;

@RequiredArgsConstructor
@Service
public class SessionService {

    public SessionUser createSessionUser(Member member) {
        return new SessionUser(member);
    }
}
