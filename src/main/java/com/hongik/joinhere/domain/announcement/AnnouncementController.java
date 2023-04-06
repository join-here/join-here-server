package com.hongik.joinhere.domain.announcement;

import com.hongik.joinhere.domain.announcement.dto.CreateAnnouncementRequest;
import com.hongik.joinhere.domain.announcement.dto.CreateAnnouncementResponse;
import com.hongik.joinhere.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping("/clubs/{club-id}/announcements")
    public CommonResponse<CreateAnnouncementResponse> create(@RequestPart(value = "request") CreateAnnouncementRequest request,
                                 @RequestPart(value = "image", required = false) MultipartFile multipartFile,
                                 @PathVariable("club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.CREATED.value(), announcementService.register(request, multipartFile, clubId));
    }
}
