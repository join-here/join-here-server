package com.hongik.joinhere.dto.qna;

import com.hongik.joinhere.entity.QnaQuestion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateQnaQuestionRequest {

    private String memberId;
    private String questionContent;
}
