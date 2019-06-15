package com.community.rest.repository;

import com.community.rest.domain.Member;
import com.community.rest.domain.projection.MemberOnlyContainMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = MemberOnlyContainMemberId.class)
public interface MemberRepository extends JpaRepository<Member, Long> {
}
