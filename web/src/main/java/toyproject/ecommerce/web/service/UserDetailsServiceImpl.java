package toyproject.ecommerce.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import toyproject.ecommerce.core.domain.member.CustomUserDetails;
import toyproject.ecommerce.core.domain.member.Member;
import toyproject.ecommerce.core.domain.member.MemberRepository;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));

        return new CustomUserDetails(member);
    }

}