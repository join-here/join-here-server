package com.hongik.joinhere.controller;

import com.hongik.joinhere.dto.member.MemberRequest;
import com.hongik.joinhere.dto.member.MemberResponse;
import com.hongik.joinhere.dto.member.LoginMemberRequest;
import com.hongik.joinhere.dto.member.LoginMemberResponse;
import com.hongik.joinhere.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponse> create(@RequestBody MemberRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.join(request));
    }

    @PatchMapping
    public void update(@RequestBody MemberRequest request) {
        memberService.updateMemberInfo(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginMemberResponse> login(@RequestBody LoginMemberRequest request) {
        LoginMemberResponse response = memberService.login(request);

        if (response == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        Cookie idCookie = new Cookie("id", null);
        Cookie nameCookie = new Cookie("name", null);
        idCookie.setMaxAge(0);
        nameCookie.setMaxAge(0);
        response.addCookie(idCookie);
        response.addCookie(nameCookie);
    }

    @GetMapping("/{member-id}")
    public MemberResponse showMemberInfo(@PathVariable(name = "member-id") String memberId) {
        return memberService.findMember(memberId);
    }
}
