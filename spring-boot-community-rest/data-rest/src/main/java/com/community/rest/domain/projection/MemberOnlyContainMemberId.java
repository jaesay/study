package com.community.rest.domain.projection;

import com.community.rest.domain.Member;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "getOnlyMemberId", types = { Member.class})
public interface MemberOnlyContainMemberId {
    String getMemberId();
}
