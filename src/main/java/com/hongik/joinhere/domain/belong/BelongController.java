package com.hongik.joinhere.domain.belong;

import com.hongik.joinhere.domain.belong.dto.*;
import com.hongik.joinhere.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BelongController {

    private final BelongService belongService;

    @GetMapping("/belongs/me")
    public CommonResponse<List<ShowMyBelongResponse>> showMyBelongs() {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), belongService.findMyBelongs());
    }

    @GetMapping("/clubs/{club-id}/belongs")
    public CommonResponse<List<BelongResponse>> showBelongsByClub(@PathVariable("club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), belongService.showClubMembers(clubId));
    }

    @PostMapping("/clubs/{club-id}/belongs")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<List<BelongResponse>> createBelongByMemberUsername(@RequestBody CreateBelongRequest request,
                                                                             @PathVariable("club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.CREATED.value(), belongService.registerBelongByMemberUsername(request, clubId));
    }

    @PatchMapping("/clubs/{club-id}/belongs")
    public CommonResponse<List<BelongResponse>> updateBelong(@RequestBody UpdateBelongRequest request,
                                                             @PathVariable("club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), belongService.updatePosition(request, clubId));
    }

    @DeleteMapping("/clubs/{club-id}/belongs")
    public CommonResponse<List<BelongResponse>> deleteBelong(@RequestBody DeleteBelongRequest request,
                                                             @PathVariable("club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), belongService.deleteBelong(request, clubId));
    }

    @PostMapping("/clubs/{club-id}/reviews")
    public CommonResponse<List<ReviewResponse>> createReview(@RequestBody CreateReviewRequest request,
                                                             @PathVariable("club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), belongService.registerReview(request, clubId));
    }

    @DeleteMapping("/clubs/{club-id}/reviews")
    public CommonResponse<List<ReviewResponse>> deleteReview(@RequestBody DeleteReviewRequest request,
                                                             @PathVariable("club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), belongService.deleteReview(request, clubId));
    }
}
