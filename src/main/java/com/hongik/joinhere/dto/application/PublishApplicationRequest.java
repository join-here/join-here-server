package com.hongik.joinhere.dto.application;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublishApplicationRequest {

    private String memberId;
    private Long applicationId;
    private String passState;
}
