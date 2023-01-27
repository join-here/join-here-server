package com.hongik.joinhere.service;

import com.hongik.joinhere.S3Service;
import com.hongik.joinhere.dto.club.*;
import com.hongik.joinhere.dto.qna.ShowQnaResponse;
import com.hongik.joinhere.dto.review.ReviewResponse;
import com.hongik.joinhere.entity.*;
import com.hongik.joinhere.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;
    private final AnnouncementRepository announcementRepository;
    private final BelongRepository belongRepository;
    private final ReviewRepository reviewRepository;
    private final QnaQuestionRepository qnaQuestionRepository;
    private final QnaAnswerRepository qnaAnswerRepository;
    private final S3Service s3Service;

    public CreateClubResponse register(CreateClubRequest request, MultipartFile multipartFile) {
        String imageUrl = null;
        List<Club> clubs = clubRepository.findByName(request.getName());

        if (clubs.isEmpty())
            return null;
        try {
            if (multipartFile != null)
                imageUrl = s3Service.uploadFiles(multipartFile, "images");
        } catch (Exception e) {
            return null;
        }

        Club club = request.toEntity(imageUrl);
        clubRepository.save(club);
        Member member = memberRepository.findById(request.getId());
        Belong belong = new Belong(null, "pre", member, club);
        belongRepository.save(belong);
        return CreateClubResponse.from(club);
    }

    public List<ShowClubResponse> findClubs() {
        List<Club> clubs = clubRepository.findAll();
        return mappingShowClubResponse(clubs);
    }

    public List<ShowClubResponse> findClubsByCategory(String category) {
        List<Club> clubs = clubRepository.findByCategory(category);
        return mappingShowClubResponse(clubs);
    }

    public List<ShowClubResponse> findClubsByQuery(String query) {
        List<Club> clubs = clubRepository.findByQuery("%" + query + "%");
        return mappingShowClubResponse(clubs);
    }

    public ShowClubInfoResponse findClubInfo(Long id) {
        Club club = clubRepository.findById(id);
        club.setView(club.getView() + 1L);
        List<Announcement> announcements = announcementRepository.findByClubId(club.getId());
        List<Review> reviews = reviewRepository.findByClubId(id);
        List<ReviewResponse> reviewResponses = new ArrayList<>();
        for (Review review : reviews)
            reviewResponses.add(ReviewResponse.from(review));
        List<QnaQuestion> qnaQuestions = qnaQuestionRepository.findByClubId(id);
        List<ShowQnaResponse> showQnaResponses = new ArrayList<>();

        for (QnaQuestion qnaQuestion : qnaQuestions) {
            List<QnaAnswer> qnaAnswers = qnaAnswerRepository.findByQnaQuestionId(qnaQuestion.getId());
            ShowQnaResponse response = new ShowQnaResponse();
            response.from(qnaQuestion, qnaAnswers);
            showQnaResponses.add(response);
        }

        if (announcements.size() == 0)
            return ShowClubInfoResponse.from(club, null, reviewResponses, showQnaResponses);
        else
            return ShowClubInfoResponse.from(club, announcements.get(announcements.size() - 1), reviewResponses, showQnaResponses);
    }

    public void updateClubInfo(UpdateClubRequest request) {
        Club club = clubRepository.findById(request.getClubId());
        club.setName(request.getName());
        club.setCategory(request.getCategory());
        club.setArea(request.getArea());
        club.setImageUrl(request.getImage());
        club.setIntroduction(request.getIntroduction());
    }

    private List<ShowClubResponse> mappingShowClubResponse(List<Club> clubs) {
        List<ShowClubResponse> responses = new ArrayList<>();

        for (Club club : clubs) {
            List<Announcement> announcements = announcementRepository.findByClubId(club.getId());
            if (announcements.size() == 0)
                responses.add(ShowClubResponse.from(club, null));
            else
                responses.add(ShowClubResponse.from(club, announcements.get(announcements.size() - 1).getEndDate()));
        }
        return responses;
    }
}