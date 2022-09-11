package com.hongik.joinhere.dto.member;

import com.hongik.joinhere.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@Getter
@AllArgsConstructor
public class CreateMemberResponse {

    private String id;
    private String name;
    private String password;
    private Date birthday;
    private String phone;

    public static CreateMemberResponse from(Member member) {
        return new CreateMemberResponse(member.getId(), member.getName(), member.getPassword(), member.getBirthday(), member.getPhone());
    }
}
