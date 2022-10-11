package com.hongik.joinhere.dto.question;

import com.hongik.joinhere.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShowQuestionResponse {

    private Long questionId;
    private String content;

    public static ShowQuestionResponse from(Question question) {
        return new ShowQuestionResponse(question.getId(), question.getContent());
    }
}
