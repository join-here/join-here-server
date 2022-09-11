package com.hongik.joinhere.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    private String id;
    private String name;
    private String password;
    private Date birthday;
    private String phone;
}
