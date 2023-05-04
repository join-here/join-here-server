package com.hongik.joinhere.domain.application.answer;

import com.hongik.joinhere.domain.application.answer.dto.CreateApplicationAnswerRequest;
import com.hongik.joinhere.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationAnswerController {

    private final ApplicationAnswerService applicationAnswerService;

    @PostMapping("/announcements/{announcement-id}/applications")
    public CommonResponse<?> create(@RequestBody List<CreateApplicationAnswerRequest> requests, @PathVariable("announcement-id") Long announcementId) {
        applicationAnswerService.register(requests, announcementId);
        return CommonResponse.onSuccess(HttpStatus.OK.value());
    }
}
