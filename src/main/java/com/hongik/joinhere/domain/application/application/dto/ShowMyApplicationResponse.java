package com.hongik.joinhere.domain.application.application.dto;

import com.hongik.joinhere.domain.application.application.entity.Application;
import com.hongik.joinhere.domain.application.application.entity.PassState;
import com.hongik.joinhere.domain.club.entity.Club;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ShowMyApplicationResponse {

    private Long clubId;
    private String clubName;
    private Long applicationId;
    private PassState passState;
    private Boolean informState;
    private LocalDateTime applicationCreatedAt;

    public static ShowMyApplicationResponse from(Club club, Application application) {
        PassState passState;

        if (!application.getAnnouncement().getInformState()) {
            passState = PassState.HOLD;
        } else {
            passState = application.getPassState();
        }
        return ShowMyApplicationResponse.builder()
                .clubId(club.getId())
                .clubName(club.getName())
                .applicationId(application.getId())
                .passState(passState)
                .informState(application.getAnnouncement().getInformState())
                .applicationCreatedAt(application.getCreatedAt())
                .build();
    }
}
