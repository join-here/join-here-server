package com.hongik.joinhere.domain.club.dto;

import com.hongik.joinhere.domain.club.entity.Area;
import com.hongik.joinhere.domain.club.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateClubRequest {

    private Long clubId;
    private String name;
    private Category category;
    private Area area;
    private String introduction;
    private Boolean isImageChanged;
}
