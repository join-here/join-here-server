package com.hongik.joinhere.domain.announcement.question.dto;

import com.hongik.joinhere.domain.announcement.question.entity.AnnouncementQuestion;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShowAnnouncementQuestionResponse {

    private Long questionId;
    private String content;

    public static ShowAnnouncementQuestionResponse from(AnnouncementQuestion announcementQuestion) {
        return new ShowAnnouncementQuestionResponse(announcementQuestion.getId(), announcementQuestion.getContent());
    }
}
