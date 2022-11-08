package com.hongik.joinhere.dto.club;

import com.hongik.joinhere.dto.qna.ShowQnaResponse;
import com.hongik.joinhere.dto.review.CreateReviewResponse;
import com.hongik.joinhere.entity.Announcement;
import com.hongik.joinhere.entity.Club;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ShowClubInfoResponse {

    private Club club;
    private Announcement announcement;
    private List<CreateReviewResponse> reviews;
    private List<ShowQnaResponse> qnas;

    public static ShowClubInfoResponse from(Club club, Announcement announcement, List<CreateReviewResponse> reviews, List<ShowQnaResponse> qnas) {
        return new ShowClubInfoResponse(club, transfer(announcement), reviews, qnas);
    }

    private static Announcement transfer(Announcement announcement) {
        if (announcement == null)
            return null;
        return new Announcement(announcement.getId(), announcement.getTitle(), announcement.getDescription(), announcement.getPoster(), announcement.getStartDate(), announcement.getEndDate(), null);
    }
}
