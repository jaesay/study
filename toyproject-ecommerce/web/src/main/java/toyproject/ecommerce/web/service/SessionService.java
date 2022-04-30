package toyproject.ecommerce.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toyproject.ecommerce.core.domain.entity.Member;
import toyproject.ecommerce.web.config.auth.dto.SessionUser;

@RequiredArgsConstructor
@Service
public class SessionService {

    public SessionUser createSessionUser(Member member) {
        return new SessionUser(member);
    }
}
