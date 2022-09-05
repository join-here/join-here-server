package com.hongik.joinhere.controller;

import com.hongik.joinhere.domain.Member;
import com.hongik.joinhere.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public Member join(@RequestBody Member member) {
        memberService.join(member);
        return member;
    }
}
