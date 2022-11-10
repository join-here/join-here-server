package com.hongik.joinhere.dto.review;

import com.hongik.joinhere.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewResponse {

    private Long reviewId;
    private String reviewContent;
    private LocalDateTime reviewTime;
    private String memberId;

    public static ReviewResponse from(Review review) {
        return new ReviewResponse(review.getId(), review.getContent(), review.getTime(), review.getMember().getId());
    }
}
