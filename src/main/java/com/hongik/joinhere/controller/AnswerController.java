package com.hongik.joinhere.controller;

import com.hongik.joinhere.dto.answer.CreateAnswerRequest;
import com.hongik.joinhere.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/announcements/{announcement-id}/answers")
    public ResponseEntity<?> create(@RequestBody List<CreateAnswerRequest> requests, @PathVariable("announcement-id") Long announcementId) {
        answerService.register(requests, announcementId);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
