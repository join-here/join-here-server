package com.hongik.joinhere.dto.question;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShowQuestionResponse {

    private Long questionId;
    private String content;
}
