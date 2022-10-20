package com.hongik.joinhere.dto.application;

import com.hongik.joinhere.entity.Application;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ShowApplicantResponse {

    private String memberId;
    private String memberName;
    private Long applicationId;
    private LocalDateTime applicationTime;
    private String passState;

    public static ShowApplicantResponse from(Application application) {
        return new ShowApplicantResponse(application.getMember().getId(), application.getMember().getName(), application.getId(), application.getTime(), application.getPassState());
    }
}
