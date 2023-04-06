package com.hongik.joinhere.domain.club.dto;

import com.hongik.joinhere.domain.club.entity.Club;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateClubResponse {

    private Long clubId;

    public static CreateClubResponse from(Club club) {
        return new CreateClubResponse(club.getId());
    }
}
