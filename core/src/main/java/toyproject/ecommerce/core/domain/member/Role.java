package toyproject.ecommerce.core.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN("ROLE_ADMIN", "관리자"),
    MANAGER("ROLE_MANAGER", "중간 관리자"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;

}