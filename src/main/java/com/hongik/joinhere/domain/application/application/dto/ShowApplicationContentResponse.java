package com.hongik.joinhere.domain.application.application.dto;

import com.hongik.joinhere.domain.application.answer.entity.ApplicationAnswer;
import com.hongik.joinhere.domain.announcement.question.entity.AnnouncementQuestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ShowApplicationContentResponse {

    private Long questionId;
    private String questionContent;
    private Long answerId;
    private String answerContent;

    public static ShowApplicationContentResponse from(AnnouncementQuestion announcementQuestion, ApplicationAnswer applicationAnswer) {
        return ShowApplicationContentResponse.builder()
                .questionId(announcementQuestion.getId())
                .questionContent(announcementQuestion.getContent())
                .answerId(applicationAnswer.getId())
                .answerContent(applicationAnswer.getContent())
                .build();
    }
}
