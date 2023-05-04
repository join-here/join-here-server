package com.hongik.joinhere.domain.club.dto;

import com.hongik.joinhere.domain.announcement.announcement.dto.AnnouncementResponse;
import com.hongik.joinhere.domain.qna.dto.QnaResponse;
import com.hongik.joinhere.domain.belong.dto.ReviewResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ShowClubInfoResponse {

    private ClubResponse club;
    private AnnouncementResponse announcement;
    private List<ReviewResponse> reviews;
    private List<QnaResponse> qnas;

    public static ShowClubInfoResponse from(ClubResponse clubResponse, AnnouncementResponse announcementResponse, List<ReviewResponse> reviews, List<QnaResponse> qnas) {
        return new ShowClubInfoResponse(clubResponse, announcementResponse, reviews, qnas);
    }
}
