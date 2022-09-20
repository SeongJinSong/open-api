package com.assignment.openapi.core.member.application;

import com.assignment.openapi.core.member.domain.Member;
import com.assignment.openapi.core.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void saveAnyMember(){
        memberRepository.save(Member.builder().name("web").build());
    }

    @Transactional(readOnly = true)
    public Member findAnyMember(){
        return memberRepository.findById(1L).get();
    }
}