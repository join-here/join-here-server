package com.hongik.joinhere.controller;

import com.hongik.joinhere.dto.club.CreateClubRequest;
import com.hongik.joinhere.dto.club.CreateClubResponse;
import com.hongik.joinhere.dto.club.ShowClubResponse;
import com.hongik.joinhere.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

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
    public ResponseEntity<CreateClubResponse> create(@RequestBody CreateClubRequest request, @CookieValue("id") String memberId) {
        CreateClubResponse response = clubService.register(request, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/categories/{category}")
    public ResponseEntity<List<ShowClubResponse>> showClubsByCategory(@PathVariable String category) {
        List<ShowClubResponse> responses = clubService.findClubsByCategory(category);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}