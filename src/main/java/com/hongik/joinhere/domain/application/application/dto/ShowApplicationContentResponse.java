package com.hongik.joinhere.domain.application.application.dto;

import com.hongik.joinhere.domain.application.answer.entity.ApplicationAnswer;
import com.hongik.joinhere.domain.application.question.entity.ApplicationQuestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ShowApplicationContentResponse {

    private Long questionId;
    private String questionContent;
    private Long answerId;
    private String answerContent;

    public static ShowApplicationContentResponse from(ApplicationQuestion applicationQuestion, ApplicationAnswer applicationAnswer) {
        return ShowApplicationContentResponse.builder()
                .questionId(applicationQuestion.getId())
                .questionContent(applicationQuestion.getContent())
                .answerId(applicationAnswer.getId())
                .answerContent(applicationAnswer.getContent())
                .build();
    }
}
