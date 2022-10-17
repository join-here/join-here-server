package com.hongik.joinhere.controller;

import com.hongik.joinhere.dto.announcement.CreateAnnouncementRequest;
import com.hongik.joinhere.dto.announcement.CreateAnnouncementResponse;
import com.hongik.joinhere.dto.club.*;
import com.hongik.joinhere.entity.Announcement;
import com.hongik.joinhere.service.AnnouncementService;
import com.hongik.joinhere.service.ClubService;
import org.aspectj.weaver.ast.Instanceof;
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

    @Autowired
    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping
    public ResponseEntity<List<ShowClubResponse>> showAll() {
        List<ShowClubResponse> responses = clubService.findClubs();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PostMapping
    public ResponseEntity<CreateClubResponse> create(@RequestBody CreateClubRequest request) {
        CreateClubResponse response = clubService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

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
}