package com.hongik.joinhere.controller;

import com.hongik.joinhere.dto.application.ShowApplicantResponse;
import com.hongik.joinhere.dto.application.ShowApplicationResponse;
import com.hongik.joinhere.dto.application.UpdateApplicantRequest;
import com.hongik.joinhere.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/clubs/{club-id}/applications")
    public List<ShowApplicantResponse> showApplicantsInfo(@PathVariable(name = "club-id") Long clubId) {
        return applicationService.findApplicants(clubId);
    }

    @PatchMapping("/clubs/{club-id}/applications")
    public List<ShowApplicantResponse> updateApplicantsInfo(@RequestBody List<UpdateApplicantRequest> requests, @PathVariable(name = "club-id") Long clubId) {
        return applicationService.updateApplicantsPassState(requests);
    }

    @GetMapping("/members/{member-id}/applications/{application-id}")
    public List<ShowApplicationResponse> showApplications(@PathVariable(name = "member-id") String memberId, @PathVariable(name = "application-id") Long applicationId) {
        return applicationService.findApplications(memberId, applicationId);
    }
}
