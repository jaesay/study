package toyproject.ecommerce.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.ecommerce.core.domain.member.Member;
import toyproject.ecommerce.core.domain.member.MemberRepository;
import toyproject.ecommerce.core.domain.member.Role;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Long signUp(Member member) {
        validateDuplicateMember(member);
        //Todo setter 쓰면 안됨, DDD에 맞게 위치 수정해야 함
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRole(Role.USER);
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
