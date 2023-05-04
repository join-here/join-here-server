package com.hongik.joinhere.domain.application.application.dto;

import com.hongik.joinhere.domain.application.application.entity.PassState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicantRequest {

    private Long applicationId;
    private PassState passState;
}