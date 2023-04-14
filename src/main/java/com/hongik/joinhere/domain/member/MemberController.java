package com.hongik.joinhere.domain.member;

import com.hongik.joinhere.domain.member.dto.UpdateMemberRequest;
import com.hongik.joinhere.domain.member.dto.MemberResponse;
import com.hongik.joinhere.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members/me")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public CommonResponse<MemberResponse> showMyInfo() {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), memberService.findMemberInfo());
    }

    @PatchMapping
    public CommonResponse<?> updateMyInfo(@RequestBody UpdateMemberRequest request) {
        memberService.updateMemberInfo(request);
        return CommonResponse.onSuccess(HttpStatus.OK.value());
    }
}
