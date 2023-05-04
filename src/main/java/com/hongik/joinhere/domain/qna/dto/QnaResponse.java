package com.hongik.joinhere.domain.qna.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hongik.joinhere.domain.qna.answer.entity.QnaAnswer;
import com.hongik.joinhere.domain.qna.question.entity.QnaQuestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    @Builder
    @AllArgsConstructor
    static class Question {

        private Long id;
        private String content;
        private LocalDateTime createdAt;
        private Long memberId;
        private String memberUsername;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    static class Answer {

        private Long id;
        private String content;
        private Boolean isManager;
        private LocalDateTime createdAt;
        private Long memberId;
        private String memberUsername;
        private Long questionId;
    }

    public void from(QnaQuestion qnaQuestion, List<QnaAnswer> qnaAnswers) {
        this.question = mappingQuestion(qnaQuestion);
        this.answers = mappingAnswer(qnaAnswers);
    }

    private Question mappingQuestion(QnaQuestion qnaQuestion) {
        return Question.builder()
                .id(qnaQuestion.getId())
                .content(qnaQuestion.getContent())
                .createdAt(qnaQuestion.getCreatedAt())
                .memberId(qnaQuestion.getMember().getId())
                .memberUsername(qnaQuestion.getMember().getUsername())
                .build();
    }

    private List<Answer> mappingAnswer(List<QnaAnswer> qnaAnswers) {
        List<Answer> answers = new ArrayList<>();
        for (QnaAnswer qnaAnswer : qnaAnswers) {
            Boolean isManager;

            if (qnaAnswer.getIsManager())
                isManager = true;
            else
                isManager = false;
            Answer answer = Answer.builder()
                                .id(qnaAnswer.getId())
                                .content(qnaAnswer.getContent())
                                .isManager(isManager)
                                .createdAt(qnaAnswer.getCreatedAt())
                                .memberId(qnaAnswer.getMember().getId())
                                .memberUsername(qnaAnswer.getMember().getUsername())
                                .questionId(qnaAnswer.getQnaQuestion().getId())
                                .build();
            answers.add(answer);
        }
        return answers;
    }
}
