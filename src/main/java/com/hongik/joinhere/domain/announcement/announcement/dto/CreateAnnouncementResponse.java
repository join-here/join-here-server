package com.hongik.joinhere.domain.announcement.announcement.dto;

import com.hongik.joinhere.domain.announcement.announcement.entity.Announcement;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateAnnouncementResponse {

    private Long announcementId;

    public static CreateAnnouncementResponse from(Announcement announcement) {
        return new CreateAnnouncementResponse(announcement.getId());
    }
}
