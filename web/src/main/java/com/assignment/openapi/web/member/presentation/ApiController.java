package com.assignment.openapi.web.member.presentation;

import com.assignment.openapi.web.member.application.ApiFacade;
import com.assignment.openapi.core.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ApiController {
    private final ApiFacade apiFacade;

    @PostMapping("/")
    public void saveAnyMember(){
        apiFacade.saveAnyMember();
    }

    @GetMapping("/")
    public Member getNewMember(){
        return apiFacade.findAnyMember();
    }
}
