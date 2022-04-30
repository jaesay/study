package com.demoecommerce.domain.entity;

import com.demoecommerce.domain.enums.AccountRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter @Setter
public class CustomUserDetails extends User {

    private static final String ROLE_PREFIX = "ROLE_";

    private Account account;

    public CustomUserDetails(Account account) {
        super(account.getAccountName(), account.getPassword(), authorities(account.getRoles()));
        this.account = account;
    }

    private static Collection<? extends GrantedAuthority> authorities(Set<AccountRole> roles) {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority(ROLE_PREFIX + r.name()))
                .collect(Collectors.toSet());
    }
}
