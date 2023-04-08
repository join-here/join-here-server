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

    @GetMapping("/clubs/{club-id}/belongs")
    public CommonResponse<List<BelongResponse>> showBelongsByClub(@PathVariable("club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), belongService.showClubMembers(clubId));
    }

    @PostMapping("/clubs/{club-id}/belongs")
    public CommonResponse<List<BelongResponse>> createByMemberId(@RequestBody  CreateBelongRequest request, @PathVariable("club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.CREATED.value(), belongService.registerByMemberId(request, clubId));
    }

    @GetMapping("/belongs")
    public CommonResponse<List<ShowMyBelongResponse>> showMyClubs() {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), belongService.findMyClubs());
    }

    @PatchMapping("/belongs")
    public CommonResponse<List<BelongResponse>> update(@RequestBody UpdateBelongRequest request, @PathVariable("club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), belongService.updatePosition(request, clubId));
    }

    @DeleteMapping("/belongs")
    public CommonResponse<List<BelongResponse>> delete(@RequestBody DeleteBelongRequest request, @PathVariable("club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), belongService.delete(request, clubId));
    }

    @PostMapping("/clubs/{club-id}/reviews")
    public CommonResponse<List<ReviewResponse>> createReview(@RequestBody CreateReviewRequest request, @PathVariable(name = "club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.CREATED.value(), belongService.registerReview(request, clubId));
    }

    @DeleteMapping("/clubs/{club-id}/reviews")
    public CommonResponse<List<ReviewResponse>> deleteReview(@RequestBody DeleteReviewRequest request, @PathVariable(name = "club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.CREATED.value(), belongService.deleteReview(request, clubId));
    }
}
