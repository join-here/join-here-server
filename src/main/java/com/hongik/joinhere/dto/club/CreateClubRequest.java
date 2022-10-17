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
    private byte[] image;
    private String introduction;

    public Club toEntity() {
        return new Club(null, name, category, area, image, introduction, 0L, 0L);
    }
}
