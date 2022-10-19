package com.hongik.joinhere.controller;

import com.hongik.joinhere.dto.application.ShowApplicationResponse;
import com.hongik.joinhere.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("clubs/{club-id}/applications")
    List<ShowApplicationResponse> showApplicationsInfo(@PathVariable(name = "club-id") Long clubId) {
        return applicationService.findApplications(clubId);
    }
}
