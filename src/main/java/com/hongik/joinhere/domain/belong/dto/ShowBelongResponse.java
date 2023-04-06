package com.hongik.joinhere.domain.belong.dto;

import com.hongik.joinhere.domain.belong.entity.Belong;
import com.hongik.joinhere.domain.belong.entity.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShowBelongResponse {

    private Long belongId;
    private Long memberId;
    private String memberName;
    private Position position;

    public static ShowBelongResponse from(Belong belong) {
        return new ShowBelongResponse(belong.getId(), belong.getMember().getId(), belong.getMember().getName(), belong.getPosition());
    }
}
