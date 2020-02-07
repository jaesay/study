package toyproject.ecommerce.web.config.oauth.dto;

import lombok.Getter;
import toyproject.ecommerce.core.domain.Member;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private Long cartId;

    public SessionUser(Member member, Long cartId) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.cartId = cartId;
    }
}
