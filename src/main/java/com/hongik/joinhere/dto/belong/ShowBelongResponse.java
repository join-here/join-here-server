package com.hongik.joinhere.dto.belong;

import com.hongik.joinhere.entity.Belong;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShowBelongResponse {

    private Long belongId;
    private String memberId;
    private String memberName;
    private String position;

    public static ShowBelongResponse from(Belong belong) {
        return new ShowBelongResponse(belong.getId(), belong.getMember().getId(), belong.getMember().getName(), belong.getPosition());
    }
}
