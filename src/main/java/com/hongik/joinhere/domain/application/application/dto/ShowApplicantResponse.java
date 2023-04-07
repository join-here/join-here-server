package com.hongik.joinhere.domain.application.application.dto;

import com.hongik.joinhere.domain.application.application.entity.Application;
import com.hongik.joinhere.domain.application.application.entity.PassState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ShowApplicantResponse {

    private Long memberId;
    private String memberName;
    private Long applicationId;
    private LocalDateTime applicationTime;
    private PassState passState;

    public static ShowApplicantResponse from(Application application) {
        return ShowApplicantResponse.builder()
                .memberId(application.getMember().getId())
                .memberName(application.getMember().getName())
                .applicationId(application.getId())
                .applicationTime(application.getCreatedAt())
                .passState(application.getPassState())
                .build();
    }
}
