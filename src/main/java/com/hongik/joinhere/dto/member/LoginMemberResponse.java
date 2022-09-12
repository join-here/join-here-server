package com.hongik.joinhere.dto.member;

import com.hongik.joinhere.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginMemberResponse {

    private String id;
    private String name;

    public static LoginMemberResponse from(Member member) {
        return new LoginMemberResponse(member.getId(), member.getName());
    }
}
