package com.hongik.joinhere.domain.belong;

import com.hongik.joinhere.domain.dto.belong.CreateBelongRequest;
import com.hongik.joinhere.domain.dto.belong.DeleteBelongRequest;
import com.hongik.joinhere.domain.dto.belong.ShowBelongResponse;
import com.hongik.joinhere.domain.dto.belong.UpdateBelongRequest;
import com.hongik.joinhere.domain.dto.belong.ShowMyBelongResponse;
import com.hongik.joinhere.domain.belong.BelongService;
import com.hongik.joinhere.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BelongController {

    private final BelongService belongService;

    @GetMapping("/clubs/{club-id}/belongs")
    public CommonResponse<List<ShowBelongResponse>> showBelongsByClub(@PathVariable("club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), belongService.showClubMembers(clubId));
    }

    @PostMapping("/clubs/{club-id}/belongs")
    public ResponseEntity<List<ShowBelongResponse>> create(@RequestBody  CreateBelongRequest request, @PathVariable("club-id") Long clubId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(belongService.register(request, clubId));
    }

    @PatchMapping("/clubs/{club-id}/belongs")
    public List<ShowBelongResponse> update(@RequestBody UpdateBelongRequest request, @PathVariable("club-id") Long clubId) {
        return belongService.updatePosition(request, clubId);
    }

    @DeleteMapping("/clubs/{club-id}/belongs")
    public List<ShowBelongResponse> delete(@RequestBody DeleteBelongRequest request, @PathVariable("club-id") Long clubId) {
        return belongService.delete(request, clubId);
    }

    @GetMapping("members/{member-id}/belongs")
    public ResponseEntity<List<ShowMyBelongResponse>> showMyClubs(@PathVariable("member-id") String memberId) {
        System.out.println(memberId);
        List<ShowMyBelongResponse> responses = belongService.findBelongByMemberId(memberId);
        if (responses != null)
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        else
            return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
