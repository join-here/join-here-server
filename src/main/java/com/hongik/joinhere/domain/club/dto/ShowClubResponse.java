package com.hongik.joinhere.domain.club.dto;

import com.hongik.joinhere.domain.club.entity.Area;
import com.hongik.joinhere.domain.club.entity.Category;
import com.hongik.joinhere.domain.club.entity.Club;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@Getter
@AllArgsConstructor
public class ShowClubResponse {

    private Long id;
    private String name;
    private Category category;
    private Area area;
    private String imageUrl;
    private String introduction;
    private Long view;
    private Date endDate;

    public static ShowClubResponse from(Club club, Date endDate) {
        return new ShowClubResponse(club.getId(), club.getName(), club.getCategory(), club.getArea(), club.getImageUrl(), club.getIntroduction(), club.getView(), endDate);
    }
}
