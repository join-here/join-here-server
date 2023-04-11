package com.hongik.joinhere.domain.application.answer.dto;

import com.hongik.joinhere.domain.application.answer.entity.ApplicationAnswer;
import com.hongik.joinhere.domain.application.application.entity.Application;
import com.hongik.joinhere.domain.announcement.question.entity.AnnouncementQuestion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateApplicationAnswerRequest {

    private String answerContent;
    private Long questionId;

    public ApplicationAnswer toEntity(Application application, AnnouncementQuestion announcementQuestion) {
        return ApplicationAnswer.builder()
                .content(answerContent)
                .application(application)
                .announcementQuestion(announcementQuestion)
                .build();
    }
}