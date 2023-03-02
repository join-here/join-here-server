package com.hongik.joinhere.domain.user.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String username;

    @Column
    private String password;

    @Column(length = 20)
    private String nickname;

    @Column
    private Date birthday;

    @Column(length = 20)
    private String phone;

    @Column(columnDefinition = "varchar(20) default 'ROLE_USER'")
    @Enumerated(EnumType.STRING)
    private Authority authority;
}
