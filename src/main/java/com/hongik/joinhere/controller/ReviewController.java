package com.hongik.joinhere.controller;

import com.hongik.joinhere.dto.review.CreateReviewRequest;
import com.hongik.joinhere.dto.review.ReviewResponse;
import com.hongik.joinhere.dto.review.DeleteReviewRequest;
import com.hongik.joinhere.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/clubs/{club-id}/reviews")
    public ResponseEntity<?> create(@RequestBody CreateReviewRequest request, @PathVariable(name = "club-id") Long clubId) {
        List<ReviewResponse> responses = reviewService.register(request, clubId);
        if (responses == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    @DeleteMapping("/clubs/{club-id}/reviews")
    public List<ReviewResponse> create(@RequestBody DeleteReviewRequest request, @PathVariable(name = "club-id") Long clubId) {
        return reviewService.delete(request, clubId);
    }
}
