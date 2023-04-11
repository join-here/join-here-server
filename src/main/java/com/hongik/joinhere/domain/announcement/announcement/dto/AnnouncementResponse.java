package com.hongik.joinhere.domain.announcement.announcement.dto;

import com.hongik.joinhere.domain.announcement.announcement.entity.Announcement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class AnnouncementResponse {

    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean informState;

    public static AnnouncementResponse from(Announcement announcement) {
        return AnnouncementResponse.builder()
                .id(announcement.getId())
                .title(announcement.getTitle())
                .description(announcement.getDescription())
                .imageUrl(announcement.getImageUrl())
                .startDate(announcement.getStartDate())
                .endDate(announcement.getEndDate())
                .informState(announcement.getInformState())
                .build();
    }
}
