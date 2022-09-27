package com.hongik.joinhere.dto.club;

import com.hongik.joinhere.entity.Belong;
import com.hongik.joinhere.entity.Club;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShowMyClubResponse {

    private Belong belong;

    public static ShowMyClubResponse from(Belong belong) {
        return new ShowMyClubResponse(belong);
    }
}
