package com.hongik.joinhere.controller;

import com.hongik.joinhere.dto.club.CreateClubRequest;
import com.hongik.joinhere.dto.club.CreateClubResponse;
import com.hongik.joinhere.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/clubs")
public class ClubController {

    private final ClubService clubService;

    @Autowired
    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @PostMapping
    public ResponseEntity<CreateClubResponse> create(@RequestBody CreateClubRequest request, @CookieValue("id") String memberId) {
        CreateClubResponse response = clubService.register(request, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}