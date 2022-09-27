package com.hongik.joinhere.controller;

import com.hongik.joinhere.dto.announcement.CreateAnnouncementRequest;
import com.hongik.joinhere.dto.announcement.CreateAnnouncementResponse;
import com.hongik.joinhere.dto.club.*;
import com.hongik.joinhere.entity.Announcement;
import com.hongik.joinhere.service.AnnouncementService;
import com.hongik.joinhere.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/clubs")
public class ClubController {

    private final ClubService clubService;
    private final AnnouncementService announcementService;

    @Autowired
    public ClubController(ClubService clubService, AnnouncementService announcementService) {
        this.clubService = clubService;
        this.announcementService = announcementService;
    }

    @GetMapping
    public ResponseEntity<List<ShowClubResponse>> showAll() {
        List<ShowClubResponse> responses = clubService.findClubs();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

//    @PostMapping
//    public ResponseEntity<CreateClubResponse> create(@RequestBody CreateClubRequest request) {
//        String memberId = request.getId();
//        CreateClubResponse response = clubService.register(request, memberId);
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }

    @GetMapping("/{club-id}")
    public ResponseEntity<ShowClubInfoResponse> showClubInfo(@PathVariable("club-id") Long id) {
        ShowClubInfoResponse response = clubService.findClubInfo(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/categories/{category}")
    public ResponseEntity<List<ShowClubResponse>> showClubsByCategory(@PathVariable String category) {
        List<ShowClubResponse> responses = clubService.findClubsByCategory(category);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PostMapping("/{club-id}/announcements")
    public ResponseEntity<CreateAnnouncementResponse> createAnnouncement(@RequestBody CreateAnnouncementRequest request, @PathVariable("club-id") Long id) {
        System.out.println(request);
        CreateAnnouncementResponse response = announcementService.register(request, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}