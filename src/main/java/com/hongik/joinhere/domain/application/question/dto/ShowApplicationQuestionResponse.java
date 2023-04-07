package com.hongik.joinhere.domain.application.question.dto;

import com.hongik.joinhere.domain.application.question.entity.ApplicationQuestion;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShowApplicationQuestionResponse {

    private Long questionId;
    private String content;

    public static ShowApplicationQuestionResponse from(ApplicationQuestion applicationQuestion) {
        return new ShowApplicationQuestionResponse(applicationQuestion.getId(), applicationQuestion.getContent());
    }
}
