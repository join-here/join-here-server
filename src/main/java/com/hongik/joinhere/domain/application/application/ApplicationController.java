package com.hongik.joinhere.domain.application.application;

import com.hongik.joinhere.domain.application.application.dto.PublishApplicationRequest;
import com.hongik.joinhere.domain.application.application.dto.ShowApplicantResponse;
import com.hongik.joinhere.domain.application.application.dto.ShowMyApplicationResponse;
import com.hongik.joinhere.domain.application.application.dto.UpdateApplicantRequest;
import com.hongik.joinhere.domain.dto.application.*;
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

    @PatchMapping("/clubs/{club-id}/applications")
    public CommonResponse<List<ShowApplicantResponse>> updateApplicantsInfo(@RequestBody List<UpdateApplicantRequest> requests, @PathVariable(name = "club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), applicationService.updateApplicantsPassState(clubId, requests));
    }

    @PostMapping("clubs/{club-id}/applications/publish")
    public CommonResponse<?> publishApplications(@RequestBody List<PublishApplicationRequest> requests, @PathVariable(name = "club-id") Long clubId) {
        applicationService.publishApplications(requests, clubId);
        CommonResponse.onSuccess(HttpStatus.OK.value());
    }

    @GetMapping("/applications")
    public CommonResponse<List<ShowMyApplicationResponse>> showMyApplications() {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), applicationService.findMyApplications());
    }

    @GetMapping("/applications/{application-id}")
    public List<ShowApplicationContentResponse> showApplicationContents(@PathVariable(name = "application-id") Long applicationId) {
        return applicationService.findApplications(applicationId);
    }
}
