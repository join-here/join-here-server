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
    private String memberUsername;
    private String memberName;
    private Long applicationId;
    private LocalDateTime applicationCreatedAt;
    private PassState passState;

    public static ShowApplicantResponse from(Application application) {
        return ShowApplicantResponse.builder()
                .memberId(application.getMember().getId())
                .memberUsername(application.getMember().getUsername())
                .memberName(application.getMember().getName())
                .applicationId(application.getId())
                .applicationCreatedAt(application.getCreatedAt())
                .passState(application.getPassState())
                .build();
    }
}
