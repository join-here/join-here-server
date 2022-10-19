package com.hongik.joinhere.dto.application;

import com.hongik.joinhere.entity.Application;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ShowApplicationResponse {

    private String memberId;
    private String memberName;
    private Long applicationId;
    private LocalDateTime applicationTime;
    private String passState;

    public static ShowApplicationResponse from(Application application) {
        return new ShowApplicationResponse(application.getMember().getId(), application.getMember().getName(), application.getId(), application.getTime(), application.getPassState());
    }
}
