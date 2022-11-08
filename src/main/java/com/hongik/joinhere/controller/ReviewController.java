package com.hongik.joinhere.controller;

import com.hongik.joinhere.dto.review.CreateReviewRequest;
import com.hongik.joinhere.dto.review.CreateReviewResponse;
import com.hongik.joinhere.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/clubs/{club-id}/reviews")
    public ResponseEntity<?> create(@RequestBody CreateReviewRequest request, @PathVariable(name = "club-id") Long clubId) {
        List<CreateReviewResponse> responses = reviewService.register(request, clubId);
        if (responses == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }
}
