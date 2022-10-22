package com.hongik.joinhere.dto.application;

import com.hongik.joinhere.entity.Answer;
import com.hongik.joinhere.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShowApplicationContentResponse {

    private Long questionId;
    private String questionContent;
    private Long answerId;
    private String answerContent;

    public static ShowApplicationContentResponse from(Question question, Answer answer) {
        return new ShowApplicationContentResponse(question.getId(), question.getContent(), answer.getId(), answer.getContent());
    }
}
