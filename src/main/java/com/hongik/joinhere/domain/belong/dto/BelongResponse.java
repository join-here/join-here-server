package com.hongik.joinhere.domain.belong.dto;

import com.hongik.joinhere.domain.belong.entity.Belong;
import com.hongik.joinhere.domain.belong.entity.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BelongResponse {

    private Long belongId;
    private Long memberId;
    private String memberUsername;
    private String memberName;
    private Position position;

    public static BelongResponse from(Belong belong) {
        return BelongResponse.builder()
                .belongId(belong.getId())
                .memberId(belong.getMember().getId())
                .memberUsername(belong.getMember().getUsername())
                .memberName(belong.getMember().getName())
                .position(belong.getPosition())
                .build();
    }
}
