package com.hongik.joinhere.domain.belong.dto;

import com.hongik.joinhere.domain.belong.entity.Position;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBelongRequest {

    private Long belongId;
    private Position position;
}
