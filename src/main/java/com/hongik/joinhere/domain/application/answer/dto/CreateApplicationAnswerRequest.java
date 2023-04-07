package com.hongik.joinhere.domain.application.answer.dto;

import com.hongik.joinhere.domain.application.answer.entity.ApplicationAnswer;
import com.hongik.joinhere.domain.application.question.entity.ApplicationQuestion;
import com.hongik.joinhere.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateApplicationAnswerRequest {

    private String answerContent;
    private Long questionId;

    public ApplicationAnswer toEntity(Member member, ApplicationQuestion applicationQuestion) {
        return ApplicationAnswer.builder()
                .content(answerContent)
                .member(member)
                .applicationQuestion(applicationQuestion)
                .build();
    }
}