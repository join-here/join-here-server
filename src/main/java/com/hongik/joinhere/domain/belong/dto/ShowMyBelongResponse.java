package com.hongik.joinhere.domain.belong.dto;

import com.hongik.joinhere.domain.belong.entity.Belong;
import com.hongik.joinhere.domain.belong.entity.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShowMyBelongResponse {

    private Long belongId;
    private Position position;
    private Boolean hasAnnouncement;

    public static ShowMyBelongResponse from(Belong belong, Boolean hasAnnouncement) {
        return new ShowMyBelongResponse(belong.getId(), belong.getPosition(), hasAnnouncement);
    }
}
