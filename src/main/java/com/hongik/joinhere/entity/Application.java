package com.hongik.joinhere.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pass_state")
    private String passState;

    @Column(name = "inform_state")
    private String informState;

    @JoinColumn(name = "member_id")
    @OneToOne
    Member member;

    @JoinColumn(name = "announcement_id")
    @OneToOne
    Announcement announcement;
}
