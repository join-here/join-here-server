package com.hongik.joinhere.dto.club;

import com.hongik.joinhere.entity.Club;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@Getter
@AllArgsConstructor
public class ShowClubResponse {

    private Long id;
    private String name;
    private String category;
    private String area;
    private byte[] image;
    private String introduction;
    private Long view;
    private Long scrap;
    private Date endDate;

    public static ShowClubResponse from(Club club, Date endDate) {
        return new ShowClubResponse(club.getId(), club.getName(), club.getCategory(), club.getArea(), club.getImage(), club.getIntroduction(), club.getView(), club.getScrap(), endDate);
    }
}
