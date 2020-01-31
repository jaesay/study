package toyproject.ecommerce.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.ecommerce.core.domain.member.Member;
import toyproject.ecommerce.core.domain.member.MemberRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long signUp(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        int countByEmail = memberRepository.countByEmail(member.getEmail());
        if (countByEmail > 0) {
            throw new IllegalStateException(member.getEmail() + " has already been used");
        }
    }
}
