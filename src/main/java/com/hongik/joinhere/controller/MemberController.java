package com.hongik.joinhere.controller;

import com.hongik.joinhere.dto.member.CreateMemberRequest;
import com.hongik.joinhere.dto.member.CreateMemberResponse;
import com.hongik.joinhere.dto.member.LoginMemberRequest;
import com.hongik.joinhere.dto.member.LoginMemberResponse;
import com.hongik.joinhere.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@CrossOrigin(origins = "*", allowedHeaders = "*")
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
}
