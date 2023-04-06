package com.hongik.joinhere.domain.club.dto;

import com.hongik.joinhere.domain.club.entity.Area;
import com.hongik.joinhere.domain.club.entity.Category;
import com.hongik.joinhere.domain.club.entity.Club;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateClubRequest {

    private String name;
    private Category category;
    private Area area;
    private String introduction;

    public Club toEntity(String imageUrl) {
        return Club.builder()
                .name(name)
                .category(category)
                .area(area)
                .imageUrl(imageUrl)
                .introduction(introduction)
                .view(0L).build();
    }
}
