package com.hongik.joinhere.domain.announcement.question;

import com.hongik.joinhere.domain.announcement.question.dto.ShowAnnouncementQuestionResponse;
import com.hongik.joinhere.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnnouncementQuestionController {

    private final AnnouncementQuestionService announcementQuestionService;

    @GetMapping("/announcements/{announcement-id}/questions")
    public CommonResponse<List<ShowAnnouncementQuestionResponse>> showQuestions(@PathVariable("announcement-id") Long announcementId) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), announcementQuestionService.findApplicationQuestionsByAnnouncement(announcementId));
    }
}
