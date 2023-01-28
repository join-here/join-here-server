package com.hongik.joinhere.controller;

import com.hongik.joinhere.dto.announcement.CreateAnnouncementRequest;
import com.hongik.joinhere.dto.announcement.CreateAnnouncementResponse;
import com.hongik.joinhere.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping("/clubs/{club-id}/announcements")
    public ResponseEntity<CreateAnnouncementResponse> create(@RequestPart(value = "request") CreateAnnouncementRequest request,
                                                             @RequestPart(value = "image", required = false) MultipartFile multipartFile,
                                                             @PathVariable("club-id") Long clubId) {
        CreateAnnouncementResponse response = announcementService.register(request, multipartFile, clubId);
        if (response == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
