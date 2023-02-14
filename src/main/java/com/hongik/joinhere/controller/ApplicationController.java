package com.hongik.joinhere.controller;

import com.hongik.joinhere.dto.application.*;
import com.hongik.joinhere.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping("/clubs/{club-id}/applications")
    public List<ShowApplicantResponse> showApplicantsInfo(@PathVariable(name = "club-id") Long clubId) {
        return applicationService.findApplicants(clubId);
    }

    @PatchMapping("/clubs/{club-id}/applications")
    public List<ShowApplicantResponse> updateApplicantsInfo(@RequestBody List<UpdateApplicantRequest> requests, @PathVariable(name = "club-id") Long clubId) {
        return applicationService.updateApplicantsPassState(requests);
    }

    @PostMapping("clubs/{club-id}/applications/publish")
    public ResponseEntity<?> publishApplications(@RequestBody List<PublishApplicationRequest> requests, @PathVariable(name = "club-id") Long clubId) {
        applicationService.publishApplications(requests, clubId);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/members/{member-id}/applications")
    public List<ShowMyApplicationResponse> showMyApplications(@PathVariable(name = "member-id") String memberId) {
        return applicationService.findApplicationsByMemberId(memberId);
    }

    @GetMapping("/applications/{application-id}")
    public List<ShowApplicationContentResponse> showApplicationContents(@PathVariable(name = "application-id") Long applicationId) {
        return applicationService.findApplications(applicationId);
    }
}
