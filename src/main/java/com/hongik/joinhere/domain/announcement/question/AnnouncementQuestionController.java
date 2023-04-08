package com.hongik.joinhere.domain.application.question;

import com.hongik.joinhere.domain.application.question.dto.ShowApplicationQuestionResponse;
import com.hongik.joinhere.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationQuestionController {

    private final ApplicationQuestionService applicationQuestionService;

    @GetMapping("/announcements/{announcement-id}/questions")
    public CommonResponse<List<ShowApplicationQuestionResponse>> showQuestions(@PathVariable("announcement-id") Long announcementId) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), applicationQuestionService.findApplicationQuestionsByAnnouncement(announcementId));
    }
}
