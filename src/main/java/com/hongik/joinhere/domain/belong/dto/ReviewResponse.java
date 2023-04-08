package com.hongik.joinhere.domain.belong.dto;

import com.hongik.joinhere.domain.belong.entity.Belong;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewResponse {

    private String reviewContent;
    private LocalDateTime reviewCreatedAt;
    private String username;

    public static ReviewResponse from(Belong belong) {
        return new ReviewResponse(belong.getReview(), belong.getReviewCreatedAt(), belong.getMember().getUsername());
    }
}