package toyproject.ecommerce.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.ecommerce.core.domain.entity.Member;
import toyproject.ecommerce.core.repository.MemberRepository;
import toyproject.ecommerce.core.support.MessageUtil;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final CartService cartService;
    private final MessageUtil messageUtil;

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
            throw new IllegalStateException(messageUtil.getMessage("member.sign.up.username.duplication"));
        }
    }
}
