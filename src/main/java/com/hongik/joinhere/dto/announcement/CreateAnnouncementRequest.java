package com.hongik.joinhere.dto.announcement;


import com.hongik.joinhere.entity.Announcement;
import com.hongik.joinhere.entity.Club;
import com.hongik.joinhere.entity.Question;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class CreateAnnouncementRequest {

    private String id;
    private String title;
    private String description;
    private String poster;
    private Date startDate;
    private Date endDate;
    private List<String> question;

    public Announcement toAnnouncement(Club club) {
        return new Announcement(null, title, description, poster, startDate, endDate, club);
    }
}
