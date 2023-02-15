package com.hongik.joinhere.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class CreateUserRequest {

    private String username;
    private String password;
    private String nickname;
    private Date birthday;
    private String phone;
}
