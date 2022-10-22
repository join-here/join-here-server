package com.hongik.joinhere.dto.application;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateApplicantRequest {

    private Long applicationId;
    private String passState;
}