package com.hongik.joinhere.domain.qna.question.entity;

import com.hongik.joinhere.domain.club.entity.Club;
import com.hongik.joinhere.domain.member.entity.Member;
import com.hongik.joinhere.global.common.entity.TimeBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "qna_question")
public class QnaQuestion extends TimeBaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    Member member;

    @ManyToOne
    @JoinColumn(name = "club_id")
    Club club;
}
