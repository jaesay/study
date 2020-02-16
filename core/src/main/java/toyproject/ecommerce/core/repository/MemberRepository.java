package toyproject.ecommerce.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.ecommerce.core.domain.entity.Member;
import toyproject.ecommerce.core.domain.enums.Role;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    int countByEmail(String email);
    Optional<Member> findByEmailAndRole(String username, Role role);
}
