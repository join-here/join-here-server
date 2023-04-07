package com.hongik.joinhere.domain.application.answer.entity;

import com.hongik.joinhere.domain.application.question.entity.ApplicationQuestion;
import com.hongik.joinhere.domain.member.entity.Member;
import com.hongik.joinhere.global.common.entity.TimeBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "application_answer")
public class ApplicationAnswer extends TimeBaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    Member member;

    @ManyToOne
    @JoinColumn(name = "application_question_id")
    ApplicationQuestion applicationQuestion;
}
