package com.assignment.openapi.web.member.application;

import com.assignment.openapi.core.member.application.MemberService;
import com.assignment.openapi.core.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiFacade {
    private final MemberService memberService;

    public void saveAnyMember(){
        memberService.saveAnyMember();
    }

    public Member findAnyMember(){
        return memberService.findAnyMember();
    }
}
