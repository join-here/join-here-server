package com.hongik.joinhere.controller;

import com.hongik.joinhere.dto.qna.*;
import com.hongik.joinhere.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
public class QnaController {

    private final QnaService qnaService;

    @PostMapping("/clubs/{club-id}/qnas/questions")
    ResponseEntity<?> registerQuestion(@RequestBody CreateQnaQuestionRequest request, @PathVariable(name = "club-id") Long clubId) {
        List<ShowQnaResponse> responses = qnaService.registerQuestion(request, clubId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    @DeleteMapping("/clubs/{club-id}/qnas/questions")
    ResponseEntity<?> deleteQuestion(@RequestBody DeleteQnaQuestionRequest request, @PathVariable(name = "club-id") Long clubId) {
        return null;
    }

    @PostMapping("/clubs/{club-id}/qnas/answers")
    ResponseEntity<?> registerAnswer(@RequestBody CreateQnaAnswerRequest request, @PathVariable(name = "club-id") Long clubId) {
        List<ShowQnaResponse> responses = qnaService.registerAnswer(request, clubId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    @DeleteMapping("/clubs/{club-id}/qnas/answers")
    ResponseEntity<?> deleteAnswer(@RequestBody DeleteQnaAnswerRequest request, @PathVariable(name = "club-id") Long clubId) {
        return null;
    }
}
