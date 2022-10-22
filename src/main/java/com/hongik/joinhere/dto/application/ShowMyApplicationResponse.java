package com.hongik.joinhere.dto.application;

import com.hongik.joinhere.entity.Application;
import com.hongik.joinhere.entity.Club;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ShowMyApplicationResponse {

    private Long clubId;
    private String clubName;
    private Long applicationId;
    private String passState;
    private String informState;
    private LocalDateTime applicationTime;

    public static ShowMyApplicationResponse from(Club club, Application application) {
        String passState;

        if (application.getInformState().equals("n"))
            passState = "hold";
        else
            passState = application.getPassState();
        return new ShowMyApplicationResponse(club.getId(), club.getName(), application.getId(), passState, application.getInformState(), application.getTime());
    }
}
