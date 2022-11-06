package com.hongik.joinhere.dto.review;

import com.hongik.joinhere.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateReviewResponse {

    private Long reviewId;
    private String reviewContent;
    private LocalDateTime reviewTime;
    private String memberId;

    public static CreateReviewResponse from(Review review) {
        return new CreateReviewResponse(review.getId(), review.getContent(), review.getTime(), review.getMember().getId());
    }
}
