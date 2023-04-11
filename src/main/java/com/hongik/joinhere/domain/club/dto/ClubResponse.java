package com.hongik.joinhere.domain.club.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hongik.joinhere.domain.club.entity.Area;
import com.hongik.joinhere.domain.club.entity.Category;
import com.hongik.joinhere.domain.club.entity.Club;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ClubResponse {

    private Long id;
    private String name;
    private Category category;
    private Area area;
    private String imageUrl;
    private String introduction;
    private Long view;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate endDate;

    public static ClubResponse from(Club club, LocalDate endDate) {
        return new ClubResponse(club.getId(), club.getName(), club.getCategory(), club.getArea(), club.getImageUrl(), club.getIntroduction(), club.getView(), endDate);
    }
}
