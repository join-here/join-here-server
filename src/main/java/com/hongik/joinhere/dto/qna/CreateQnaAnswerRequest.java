package com.hongik.joinhere.dto.qna;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateQnaAnswerRequest {

    private String memberId;
    private Long questionId;
    private String answerContent;
}
