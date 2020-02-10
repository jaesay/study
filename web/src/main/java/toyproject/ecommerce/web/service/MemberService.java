package toyproject.ecommerce.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.ecommerce.core.domain.Member;
import toyproject.ecommerce.core.repository.MemberRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final CartService cartService;

    @Transactional
    public Long signUp(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        cartService.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        int countByEmail = memberRepository.countByEmail(member.getEmail());
        if (countByEmail > 0) {
            throw new IllegalStateException(member.getEmail() + " has already been used");
        }
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
    }
}
