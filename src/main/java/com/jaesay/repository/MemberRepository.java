package com.jaesay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jaesay.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

	Member findByMemberName(String memberName);
}
