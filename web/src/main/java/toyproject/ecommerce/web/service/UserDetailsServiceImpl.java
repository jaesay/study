package toyproject.ecommerce.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import toyproject.ecommerce.core.domain.entity.Member;
import toyproject.ecommerce.core.repository.MemberRepository;
import toyproject.ecommerce.core.support.MessageUtil;
import toyproject.ecommerce.web.config.auth.dto.CustomUserDetails;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final MessageUtil messageUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(messageUtil.getMessage("member.sign.up.credential.error")));

        return new CustomUserDetails(member);
    }
}