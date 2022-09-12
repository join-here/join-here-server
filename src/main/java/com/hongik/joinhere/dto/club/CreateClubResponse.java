package com.hongik.joinhere.dto.club;

import com.hongik.joinhere.entity.Club;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateClubResponse {

    private Long id;
    private String name;
    private String category;
    private String area;
    private byte[] image;
    private String introduction;
    private Long view;
    private Long scrap;

    public static CreateClubResponse from(Club club) {
        return new CreateClubResponse(club.getId(), club.getName(), club.getCategory(), club.getArea(), club.getImage(), club.getIntroduction(), club.getView(), club.getScrap());
    }
}
