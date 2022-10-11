package com.hongik.joinhere.controller;

import com.hongik.joinhere.dto.belong.ShowClubBelongResponse;
import com.hongik.joinhere.service.BelongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
    List<ShowClubBelongResponse> showBelongByClub(@PathVariable("club-id") Long clubId) {
        return belongService.findBelongByClubId(clubId);
    }
}
