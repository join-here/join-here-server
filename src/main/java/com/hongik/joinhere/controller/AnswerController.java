package com.hongik.joinhere.controller;

import com.hongik.joinhere.dto.answer.CreateAnswerRequest;
import com.hongik.joinhere.service.AnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/announcements/{announcement-id}/answers")
    ResponseEntity<?> create(@RequestBody List<CreateAnswerRequest> requests, @PathVariable("announcement-id") Long announcementId) {
        answerService.register(requests, announcementId);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
