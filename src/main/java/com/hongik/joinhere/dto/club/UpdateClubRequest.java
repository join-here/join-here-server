package com.hongik.joinhere.dto.club;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateClubRequest {

    private Long clubId;
    private String name;
    private String category;
    private String area;
    private String introduction;
    private Boolean isImageChanged;
}
