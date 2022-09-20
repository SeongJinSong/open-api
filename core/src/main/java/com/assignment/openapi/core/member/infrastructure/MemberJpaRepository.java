package com.assignment.openapi.core.member.infrastructure;

import com.assignment.openapi.core.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member,Long> {
}