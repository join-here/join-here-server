package com.hongik.joinhere.domain.belong.dto;

import com.hongik.joinhere.domain.belong.entity.Belong;
import com.hongik.joinhere.domain.belong.entity.Position;
import com.hongik.joinhere.domain.club.dto.ClubResponse;
import com.hongik.joinhere.domain.club.entity.Club;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ShowMyBelongResponse {

    private Long belongId;
    private Position position;
    private Boolean hasAnnouncement;
    private ClubResponse club;

    public static ShowMyBelongResponse from(Belong belong, Boolean hasAnnouncement) {
        return ShowMyBelongResponse.builder()
                .belongId(belong.getId())
                .position(belong.getPosition())
                .hasAnnouncement(hasAnnouncement)
                .club(ClubResponse.from(belong.getClub(), null))
                .build();
    }
}
