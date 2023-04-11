package com.hongik.joinhere.domain.club;

import com.hongik.joinhere.domain.club.dto.*;
import com.hongik.joinhere.domain.club.entity.Category;
import com.hongik.joinhere.domain.club.dto.ShowClubInfoResponse;
import com.hongik.joinhere.domain.club.dto.ClubResponse;
import com.hongik.joinhere.domain.club.dto.UpdateClubRequest;
import com.hongik.joinhere.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @GetMapping
    public CommonResponse<List<ClubResponse>> showAll() {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), clubService.findClubs());
    }

    @PostMapping
    public CommonResponse<CreateClubResponse> create(@RequestPart(value = "request") CreateClubRequest request,
                                 @RequestPart(value = "image", required = false) MultipartFile multipartFile) {
        return CommonResponse.onSuccess(HttpStatus.CREATED.value(), clubService.register(request, multipartFile));
    }

    @PatchMapping
    public CommonResponse<?> update(@RequestPart(value = "request") UpdateClubRequest request,
                                         @RequestPart(value = "image", required = false) MultipartFile multipartFile) {
        clubService.updateClubInfo(request, multipartFile);
        return CommonResponse.onSuccess(HttpStatus.OK.value());
    }

    @GetMapping("/search")
    public CommonResponse<List<ClubResponse>> searchClubs(@RequestParam String query) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), clubService.findClubsByQuery(query));
    }

    @GetMapping("/{club-id}")
    public CommonResponse<ShowClubInfoResponse> showClubInfo(@PathVariable("club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), clubService.findClubInfo(clubId));
    }

    @GetMapping("/categories/{category}")
    public CommonResponse<List<ClubResponse>> showClubsByCategory(@PathVariable Category category) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), clubService.findClubsByCategory(category));
    }
}