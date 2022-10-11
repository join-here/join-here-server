package com.hongik.joinhere.controller;

import com.hongik.joinhere.dto.announcement.CreateAnnouncementRequest;
import com.hongik.joinhere.dto.announcement.CreateAnnouncementResponse;
import com.hongik.joinhere.entity.Question;
import com.hongik.joinhere.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @Autowired
    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @PostMapping("clubs/{club-id}/announcements")
    public ResponseEntity<CreateAnnouncementResponse> createAnnouncement(@RequestBody CreateAnnouncementRequest request, @PathVariable("club-id") Long id) {
        CreateAnnouncementResponse response = announcementService.register(request, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
