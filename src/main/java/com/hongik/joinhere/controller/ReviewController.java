package com.hongik.joinhere.controller;

import com.hongik.joinhere.dto.review.CreateReviewRequest;
import com.hongik.joinhere.dto.review.CreateReviewResponse;
import com.hongik.joinhere.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/clubs/{club-id}/reviews")
    public ResponseEntity<?> create(@RequestBody CreateReviewRequest request, @PathVariable(name = "club-id") Long clubId) {
        List<CreateReviewResponse> responses = reviewService.register(request, clubId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }
}
