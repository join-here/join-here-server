package com.hongik.joinhere.dto.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReviewRequest {

    private String memberId;
    private String reviewContent;
}