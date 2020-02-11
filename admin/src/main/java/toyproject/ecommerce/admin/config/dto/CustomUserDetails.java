package toyproject.ecommerce.admin.config.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import toyproject.ecommerce.core.domain.Member;
import toyproject.ecommerce.core.domain.enums.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter @Setter
public class CustomUserDetails extends User {

    private Member member;

    public CustomUserDetails(Member member) {
        super(member.getName(), member.getPassword(), getAuthorities(member.getRole()));
        this.member = member;
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(role.getKey()));
        return list;
    }
}

