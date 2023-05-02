package com.hongik.joinhere.domain.belong.dto;

import com.hongik.joinhere.domain.belong.entity.Belong;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ReviewResponse {

    private Long belongId;
    private String reviewContent;
    private LocalDateTime reviewCreatedAt;
    private Long memberId;
    private String memberUsername;

    public static ReviewResponse from(Belong belong) {
        return ReviewResponse.builder()
                .belongId(belong.getId())
                .reviewContent(belong.getReview())
                .reviewCreatedAt(belong.getReviewCreatedAt())
                .memberId(belong.getMember().getId())
                .memberUsername(belong.getMember().getUsername())
                .build();
    }
}