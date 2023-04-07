package com.hongik.joinhere.domain.member.entity;

import com.hongik.joinhere.global.common.entity.TimeBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String username;

    @Column
    private String password;

    @Column(length = 20)
    private String name;

    @Column
    private Date birthday;

    @Column(length = 20)
    private String phone;

    @Column(columnDefinition = "varchar(20) default 'ROLE_USER'")
    @Enumerated(EnumType.STRING)
    private Authority authority;

    public void updatePassword(String password) {
        this.password = password;
    }

    public void  updatePhone(String phone) {
        this.phone = phone;
    }
}
