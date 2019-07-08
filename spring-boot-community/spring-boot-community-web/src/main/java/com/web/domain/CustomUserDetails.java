package com.web.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CustomUserDetails extends User {

    private static final String ROLE_PREFIX = "ROLE_";

    private Member member;

    public CustomUserDetails(Member member) {
        super(member.getMemberId(), member.getPassword(), getAuthorities(member.getRoles()));

        this.member = member;
    }

    private static List<GrantedAuthority> getAuthorities(List<Role> roles) {

        List<GrantedAuthority> list = new ArrayList<>();

        roles.forEach(
                role -> list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRoleName())));

        return list;
    }


}
