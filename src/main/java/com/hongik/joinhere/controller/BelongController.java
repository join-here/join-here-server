package com.hongik.joinhere.controller;

import com.hongik.joinhere.dto.belong.CreateBelongRequest;
import com.hongik.joinhere.dto.belong.DeleteBelongRequest;
import com.hongik.joinhere.dto.belong.ShowBelongResponse;
import com.hongik.joinhere.dto.belong.UpdateBelongRequest;
import com.hongik.joinhere.dto.belong.ShowMyBelongResponse;
import com.hongik.joinhere.service.BelongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
public class BelongController {

    private final BelongService belongService;

    @GetMapping("/clubs/{club-id}/belongs")
    public List<ShowBelongResponse> showBelongsByClub(@PathVariable("club-id") Long clubId) {
        return belongService.findBelongByClub(clubId);
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
