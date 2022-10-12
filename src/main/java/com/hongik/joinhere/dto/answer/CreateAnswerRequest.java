package com.hongik.joinhere.dto.answer;

import com.hongik.joinhere.entity.Answer;
import com.hongik.joinhere.entity.Member;
import com.hongik.joinhere.entity.Question;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateAnswerRequest {

    private String answerContent;
    private String memberId;
    private Long questionId;

    public Answer toEntity(Member member, Question question) {
        return new Answer(null, answerContent, member, question);
    }
}