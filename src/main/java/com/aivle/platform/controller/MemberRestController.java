package com.aivle.platform.controller;

import com.aivle.platform.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class MemberRestController {
    private final MemberService memberService;

    @GetMapping("/check-email/{email}")
    public Boolean checkEmail(@PathVariable String email) {
        return memberService.existsByEmail(email);
    }

}
