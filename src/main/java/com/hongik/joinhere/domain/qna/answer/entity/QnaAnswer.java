package com.hongik.joinhere.domain.qna.answer.entity;

import com.hongik.joinhere.domain.member.entity.Member;
import com.hongik.joinhere.domain.qna.question.entity.QnaQuestion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "qna_answer")
public class QnaAnswer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String content;

    @Column(name = "is_manager")
    private Boolean isManager;

    @ManyToOne
    @JoinColumn(name = "member_id")
    Member member;

    @ManyToOne
    @JoinColumn(name = "qna_question_id")
    QnaQuestion qnaQuestion;
}
