package com.hongik.joinhere.domain.application.application;

import com.hongik.joinhere.domain.application.application.dto.*;
import com.hongik.joinhere.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping("/clubs/{club-id}/applications")
    public CommonResponse<List<ShowApplicantResponse>> showApplicantsInfo(@PathVariable(name = "club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), applicationService.findApplicants(clubId));
    }

    @PostMapping("clubs/{club-id}/applications")
    public CommonResponse<?> publishApplications(@RequestBody List<ApplicantRequest> requests,
                                                 @PathVariable(name = "club-id") Long clubId) {
        applicationService.publishApplications(requests, clubId);
        return CommonResponse.onSuccess(HttpStatus.OK.value());
    }

    @PatchMapping("/clubs/{club-id}/applications")
    public CommonResponse<List<ShowApplicantResponse>> updateApplicantsPassState(@RequestBody List<ApplicantRequest> requests,
                                                                                 @PathVariable(name = "club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), applicationService.updateApplicantsPassState(requests, clubId));
    }

    @GetMapping("/applications/me")
    public CommonResponse<List<ShowMyApplicationResponse>> showMyApplications() {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), applicationService.findMyApplications());
    }

    @GetMapping("/applications/{application-id}")
    public CommonResponse<List<ShowApplicationContentResponse>> showApplicationContents(@PathVariable(name = "application-id") Long applicationId) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), applicationService.findApplications(applicationId));
    }
}
