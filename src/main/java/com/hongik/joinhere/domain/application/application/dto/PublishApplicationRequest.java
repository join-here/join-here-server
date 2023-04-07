package com.hongik.joinhere.domain.application.application.dto;

import com.hongik.joinhere.domain.application.application.entity.PassState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublishApplicationRequest {

    private Long memberId;
    private Long applicationId;
    private PassState passState;
}
