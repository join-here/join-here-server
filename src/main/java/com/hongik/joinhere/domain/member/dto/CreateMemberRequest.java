package com.hongik.joinhere.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class CreateMemberRequest {

    private String username;
    private String password;
    private String name;
    private Date birthday;
    private String phone;
}
