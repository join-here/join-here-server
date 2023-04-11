package com.hongik.joinhere.domain.member.dto;

import com.hongik.joinhere.domain.member.entity.Authority;
import com.hongik.joinhere.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;

@Getter
@Setter
public class CreateMemberRequest {

    private String username;
    private String password;
    private String name;
    private Date birthday;
    private String phone;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .username(username)
                .name(name)
                .password(passwordEncoder.encode(password))
                .birthday(birthday)
                .phone(phone)
                .authority(Authority.ROLE_USER)
                .build();
    }
}
