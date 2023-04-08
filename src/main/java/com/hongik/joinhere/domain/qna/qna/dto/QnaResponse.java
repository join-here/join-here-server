package com.hongik.joinhere.domain.qna.qna.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hongik.joinhere.domain.qna.answer.entity.QnaAnswer;
import com.hongik.joinhere.domain.qna.question.entity.QnaQuestion;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class QnaResponse {

    @JsonProperty("qnaQuestion")
    private Question question;

    @JsonProperty("qnaAnswers")
    private List<Answer> answers;

    public QnaResponse() {
    }

    @Getter
    @AllArgsConstructor
    static class Question {

        private Long id;
        private String content;
        private LocalDateTime createdAt;
        private Long memberId;
    }

    @Getter
    @AllArgsConstructor
    static class Answer {

        private Long id;
        private String content;
        private Boolean isManager;
        private LocalDateTime createdAt;
        private Long memberId;
        private Long questionId;
    }

    public void from(QnaQuestion qnaQuestion, List<QnaAnswer> qnaAnswers) {
        this.question = mappingQuestion(qnaQuestion);
        this.answers = mappingAnswer(qnaAnswers);
    }

    private Question mappingQuestion(QnaQuestion qnaQuestion) {
        return new Question(qnaQuestion.getId(), qnaQuestion.getContent(), qnaQuestion.getCreatedAt(), qnaQuestion.getMember().getId());
    }

    private List<Answer> mappingAnswer(List<QnaAnswer> qnaAnswers) {
        List<Answer> answers = new ArrayList<>();
        for (QnaAnswer qnaAnswer : qnaAnswers) {
            Boolean isManager;

            if (qnaAnswer.getIsManager())
                isManager = true;
            else
                isManager = false;
            Answer answer = new Answer(qnaAnswer.getId(), qnaAnswer.getContent(), isManager, qnaAnswer.getCreatedAt(), qnaAnswer.getMember().getId(), qnaAnswer.getQnaQuestion().getId());
            answers.add(answer);
        }
        return answers;
    }
}
