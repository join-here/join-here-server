package com.hongik.joinhere.dto.application;

import com.hongik.joinhere.entity.Answer;
import com.hongik.joinhere.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShowApplicationResponse {

    private Long questionId;
    private String questionContent;
    private Long answerId;
    private String answerContent;

    public static ShowApplicationResponse from(Question question, Answer answer) {
        return new ShowApplicationResponse(question.getId(), question.getContent(), answer.getId(), answer.getContent());
    }
}
