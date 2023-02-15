package com.hongik.joinhere.user.dto;

import com.hongik.joinhere.user.entity.User;
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
