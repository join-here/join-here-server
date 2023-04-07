package com.hongik.joinhere.domain.member.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
public class UpdateMemberRequest {

    private String password;
    private String phone;
}
