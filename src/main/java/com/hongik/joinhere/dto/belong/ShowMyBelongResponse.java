package com.hongik.joinhere.dto.belong;

import com.hongik.joinhere.entity.Belong;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShowMyBelongResponse {

    private Belong belong;
    private Boolean hasAnnouncement;

    public static ShowMyBelongResponse from(Belong belong, Boolean hasAnnouncement) {
        Belong responseBelong = new Belong(belong.getId(), belong.getPosition(), null, belong.getClub());
        return new ShowMyBelongResponse(responseBelong, hasAnnouncement);
    }
}
