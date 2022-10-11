package com.hongik.joinhere.controller;

import com.hongik.joinhere.dto.belong.CreateBelongRequest;
import com.hongik.joinhere.dto.belong.ShowBelongResponse;
import com.hongik.joinhere.service.BelongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class BelongController {

    private final BelongService belongService;

    @Autowired
    public BelongController(BelongService belongService) {
        this.belongService = belongService;
    }

    @GetMapping("clubs/{club-id}/belong")
    List<ShowBelongResponse> showBelongsByClub(@PathVariable("club-id") Long clubId) {
        return belongService.findBelongByClub(clubId);
    }

    @PostMapping("clubs/{club-id}/belong")
    ResponseEntity<List<ShowBelongResponse>> create(@RequestBody  CreateBelongRequest request, @PathVariable("club-id") Long clubId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(belongService.register(request, clubId));
    }
}
