package com.hongik.joinhere.domain.announcement.dto;

import com.hongik.joinhere.domain.announcement.entity.Announcement;
import com.hongik.joinhere.domain.club.entity.Club;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class CreateAnnouncementRequest {

    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private List<String> question;

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
}
