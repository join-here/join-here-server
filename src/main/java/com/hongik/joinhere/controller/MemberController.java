package com.hongik.joinhere.controller;

import com.hongik.joinhere.domain.Member;
import com.hongik.joinhere.dto.CreateMemberRequest;
import com.hongik.joinhere.dto.CreateMemberResponse;
import com.hongik.joinhere.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<CreateMemberResponse> create(@RequestBody CreateMemberRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.join(request));
    }

    @PostMapping("/login")
    public String login(@RequestBody Member member) {
        return "";
    }

    @PostMapping("/logout")
    public String logout() {
        return "";
    }
}
