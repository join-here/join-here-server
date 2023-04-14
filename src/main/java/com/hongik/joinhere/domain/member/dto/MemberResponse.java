package com.hongik.joinhere.domain.member.dto;

import com.hongik.joinhere.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Date;

@Getter
@Builder
@AllArgsConstructor
public class MemberResponse {

    private Long id;
    private String username;
    private String name;
    private Date birthday;
    private String phone;

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .username(member.getUsername())
                .name(member.getName())
                .birthday(member.getBirthday())
                .phone(member.getPhone())
                .build();
    }
}
