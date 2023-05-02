package com.hongik.joinhere.domain.announcement.announcement.dto;

import com.hongik.joinhere.domain.announcement.announcement.entity.Announcement;
import com.hongik.joinhere.domain.announcement.question.entity.AnnouncementQuestion;
import com.hongik.joinhere.domain.club.entity.Club;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreateAnnouncementRequest {

    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> questions;

    public Announcement toAnnouncement(Club club, String imageUrl) {
        return Announcement.builder()
                .title(title)
                .description(description)
                .imageUrl(imageUrl)
                .startDate(startDate)
                .endDate(endDate)
                .informState(false)
                .club(club)
                .build();
    }

    public List<AnnouncementQuestion> toAnnouncementQuestion(Announcement announcement) {
        List<AnnouncementQuestion> announcementQuestions = new ArrayList<>();
        for (String content : questions) {
            AnnouncementQuestion announcementQuestion = AnnouncementQuestion.builder()
                    .content(content)
                    .announcement(announcement)
                    .build();
            announcementQuestions.add(announcementQuestion);
        }
        return announcementQuestions;
    }
}
