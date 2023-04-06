package com.hongik.joinhere.domain.belong.entity;

import com.hongik.joinhere.domain.club.entity.Club;
import com.hongik.joinhere.domain.member.entity.Member;
import com.hongik.joinhere.global.common.entity.TimeBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Belong extends TimeBaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(9) default 'NORMAL'")
    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(length = 1000)
    private String review;

    @Column(name = "review_created_at")
    private LocalDateTime reviewCreatedAt;

    @ManyToOne
    @JoinColumn(name = "member_id")
    Member member;

    @ManyToOne
    @JoinColumn(name = "club_id")
    Club club;
}
