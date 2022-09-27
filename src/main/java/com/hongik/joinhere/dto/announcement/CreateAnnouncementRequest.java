package com.hongik.joinhere.dto.announcement;


import com.hongik.joinhere.entity.Announcement;
import com.hongik.joinhere.entity.Club;
import com.hongik.joinhere.entity.Question;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class CreateAnnouncementRequest {

    private String id;
    private String title;
    private String description;
    private Byte[] poster;
    private Date startDate;
    private Date endDate;
    //    private List<QuestionContent> question;
    private String[] question;

    public Announcement toAnnouncement(Club club) {
        return new Announcement(null, title, description, poster, startDate, endDate, club);
    }

    public static class QuestionContent {

        private String content;
    }

    @Override
    public String toString() {
        return "CreateAnnouncementRequest{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", poster=" + Arrays.toString(poster) +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", question=" + question +
                '}';
    }
}
