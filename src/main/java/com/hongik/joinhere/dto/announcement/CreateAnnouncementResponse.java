package com.hongik.joinhere.dto.announcement;

import com.hongik.joinhere.entity.Announcement;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateAnnouncementResponse {

    private Long id;

    public static CreateAnnouncementResponse from(Announcement announcement) {
        return new CreateAnnouncementResponse(announcement.getId());
    }
}
