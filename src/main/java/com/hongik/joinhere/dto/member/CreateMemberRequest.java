package com.hongik.joinhere.dto;

import com.hongik.joinhere.domain.Member;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
public class CreateMemberRequest {

    private String id;
    private String name;
    private String password;
    private Date birthday;
    private String phone;

    public Member toEntity() {
        return new Member(id, name, password, birthday, phone);
    }
}
