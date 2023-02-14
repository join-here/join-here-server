package com.hongik.joinhere.controller;

import com.hongik.joinhere.dto.question.ShowQuestionResponse;
import com.hongik.joinhere.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/announcements/{announcement-id}/questions")
    public List<ShowQuestionResponse> showQuestions(@PathVariable("announcement-id") Long announcementId) {
        return questionService.findQuestionsByAnnouncement(announcementId);
    }
}
