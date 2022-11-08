package com.hongik.joinhere.dto.qna;

import com.hongik.joinhere.entity.QnaAnswer;
import com.hongik.joinhere.entity.QnaQuestion;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ShowQnaResponse {

    private Question question;
    private List<Answer> answers;

    public ShowQnaResponse() {
    }

    @Getter
    @AllArgsConstructor
    static class Question {

        private Long id;
        private String content;
        private LocalDateTime time;
        private String memberId;
    }

    @Getter
    @AllArgsConstructor
    static class Answer {

        private Long id;
        private String content;
        private Boolean isManager;
        private LocalDateTime time;
        private String memberId;
        private Long questionId;
    }

    public void from(QnaQuestion qnaQuestion, List<QnaAnswer> qnaAnswers) {
        this.question = mappingQuestion(qnaQuestion);
        this.answers = mappingAnswer(qnaAnswers);
    }

    private Question mappingQuestion(QnaQuestion qnaQuestion) {
        return new Question(qnaQuestion.getId(), qnaQuestion.getContent(), qnaQuestion.getTime(), qnaQuestion.getMember().getId());
    }

    private List<Answer> mappingAnswer(List<QnaAnswer> qnaAnswers) {
        List<Answer> answers = new ArrayList<>();
        for (QnaAnswer qnaAnswer : qnaAnswers) {
            Boolean isManager;

            if (qnaAnswer.getIsManager().equals("y"))
                isManager = true;
            else
                isManager = false;
            Answer answer = new Answer(qnaAnswer.getId(), qnaAnswer.getContent(), isManager, qnaAnswer.getTime(), qnaAnswer.getMember().getId(), qnaAnswer.getQnaQuestion().getId());
            answers.add(answer);
        }
        return answers;
    }
}
