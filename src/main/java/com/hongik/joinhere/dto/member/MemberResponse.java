package com.hongik.joinhere.dto.member;

import com.hongik.joinhere.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@Getter
@AllArgsConstructor
public class MemberResponse {

    private String id;
    private String name;
    private String password;
    private Date birthday;
    private String phone;

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getName(), member.getPassword(), member.getBirthday(), member.getPhone());
    }
}
