package com.hongik.joinhere.domain.member.dto;

import com.hongik.joinhere.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@Getter
@AllArgsConstructor
public class MemberResponse {

    private Long id;
    private String name;
    private Date birthday;
    private String phone;

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getName(), member.getBirthday(), member.getPhone());
    }
}
