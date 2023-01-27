package com.hongik.joinhere.dto.club;

import com.hongik.joinhere.entity.Club;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class CreateClubRequest {

    private String id;
    private String name;
    private String category;
    private String area;
    private String introduction;

    public Club toEntity(String imageUrl) {
        return new Club(null, name, category, area, imageUrl, introduction, 0L, 0L);
    }
}
