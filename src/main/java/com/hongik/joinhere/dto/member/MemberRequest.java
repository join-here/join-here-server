package com.hongik.joinhere.dto.member;

import com.hongik.joinhere.entity.Member;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
public class MemberRequest {

    private String id;
    private String name;
    private String password;
    private Date birthday;
    private String phone;

    public Member toEntity() {
        return new Member(id, name, password, birthday, phone);
    }
}
