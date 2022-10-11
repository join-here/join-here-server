package com.hongik.joinhere.controller;

import com.hongik.joinhere.dto.question.ShowQuestionResponse;
import com.hongik.joinhere.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class QuestionController {

    private QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/announcements/{announcement-id}/questions")
    List<ShowQuestionResponse> showQuestions(@PathVariable("announcement-id") Long announcementId) {
        return questionService.findQuestionsByAnnouncement(announcementId);
    }
}
