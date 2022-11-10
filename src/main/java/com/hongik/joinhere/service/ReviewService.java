package com.hongik.joinhere.service;

import com.hongik.joinhere.dto.review.CreateReviewRequest;
import com.hongik.joinhere.dto.review.DeleteReviewRequest;
import com.hongik.joinhere.dto.review.ReviewResponse;
import com.hongik.joinhere.entity.Belong;
import com.hongik.joinhere.entity.Club;
import com.hongik.joinhere.entity.Member;
import com.hongik.joinhere.entity.Review;
import com.hongik.joinhere.repository.BelongRepository;
import com.hongik.joinhere.repository.ClubRepository;
import com.hongik.joinhere.repository.MemberRepository;
import com.hongik.joinhere.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ReviewRepository reviewRepository;
    private final BelongRepository belongRepository;

    public List<ReviewResponse> register(CreateReviewRequest request, Long clubId) {
        Club club = clubRepository.findById(clubId);
        Member member = memberRepository.findById(request.getMemberId());
        List<Belong> belongs = belongRepository.findByMemberIdAndClubId(request.getMemberId(), clubId);
        if (belongs.isEmpty())
            return null;
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        Review saveReview = new Review(null, request.getReviewContent(), now, member, club);
        reviewRepository.save(saveReview);
        return mappingResponse(clubId);
    }

    public List<ReviewResponse> delete(DeleteReviewRequest request, Long clubId) {
        reviewRepository.delete(reviewRepository.findById(request.getReviewId()));
        return mappingResponse(clubId);
    }

    private List<ReviewResponse> mappingResponse(Long clubId) {
        List<Review> reviews = reviewRepository.findByClubId(clubId);
        List<ReviewResponse> responses = new ArrayList<>();
        for (Review review : reviews)
            responses.add(ReviewResponse.from(review));
        return responses;
    }
}
