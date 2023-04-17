package com.hongik.joinhere.domain.club;

import com.hongik.joinhere.domain.announcement.announcement.dto.AnnouncementResponse;
import com.hongik.joinhere.domain.announcement.announcement.entity.Announcement;
import com.hongik.joinhere.domain.announcement.announcement.repository.AnnouncementRepository;
import com.hongik.joinhere.domain.auth.security.SecurityUtil;
import com.hongik.joinhere.domain.belong.entity.Belong;
import com.hongik.joinhere.domain.belong.entity.Position;
import com.hongik.joinhere.domain.belong.repository.BelongRepository;
import com.hongik.joinhere.domain.club.dto.*;
import com.hongik.joinhere.domain.club.entity.Category;
import com.hongik.joinhere.domain.club.entity.Club;
import com.hongik.joinhere.domain.club.repository.ClubRepository;
import com.hongik.joinhere.domain.club.dto.ShowClubInfoResponse;
import com.hongik.joinhere.domain.club.dto.ClubResponse;
import com.hongik.joinhere.domain.club.dto.UpdateClubRequest;
import com.hongik.joinhere.domain.qna.answer.repository.QnaAnswerRepository;
import com.hongik.joinhere.domain.qna.qna.dto.QnaResponse;
import com.hongik.joinhere.domain.belong.dto.ReviewResponse;
import com.hongik.joinhere.domain.member.entity.Member;
import com.hongik.joinhere.domain.member.repository.MemberRepository;
import com.hongik.joinhere.domain.qna.answer.entity.QnaAnswer;
import com.hongik.joinhere.domain.qna.question.entity.QnaQuestion;
import com.hongik.joinhere.domain.qna.question.repository.QnaQuestionRepository;
import com.hongik.joinhere.global.error.ErrorCode;
import com.hongik.joinhere.global.error.exception.BadRequestException;
import com.hongik.joinhere.infra.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
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
    private final QnaQuestionRepository qnaQuestionRepository;
    private final QnaAnswerRepository qnaAnswerRepository;
    private final S3Service s3Service;

    public CreateClubResponse register(CreateClubRequest request, MultipartFile multipartFile) {
        String imageUrl = null;
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> (new BadRequestException(ErrorCode.MEMBER_NOT_FOUND)));
        if (clubRepository.existsByName(request.getName())) {
            throw new BadRequestException(ErrorCode.DUPLICATE_CLUB);
        }
        try {
            if (multipartFile != null && !multipartFile.isEmpty()) {
                imageUrl = s3Service.uploadFiles(multipartFile, "images");
            }
        } catch (Exception e) {
            throw new BadRequestException(ErrorCode.S3_CONNECTION_ERROR);
        }
        Club club = request.toEntity(imageUrl);
        clubRepository.save(club);
        Belong belong = Belong.builder()
                        .position(Position.PRESIDENT)
                        .member(member)
                        .club(club)
                        .build();
        belongRepository.save(belong);
        return CreateClubResponse.from(club);
    }

    public List<ClubResponse> findClubs() {
        return mappingShowClubResponse(clubRepository.findAll());
    }

    public List<ClubResponse> findClubsByCategory(Category category) {
        return mappingShowClubResponse(clubRepository.findByCategory(category));
    }

    public List<ClubResponse> findClubsByQuery(String query) {
        return mappingShowClubResponse(clubRepository.findByNameContaining(query));
    }

    public ShowClubInfoResponse findClubInfo(Long clubId) {
        Club club = clubRepository.findById(clubId)
                        .orElseThrow(() -> new BadRequestException(ErrorCode.CLUB_NOT_FOUND));
        club.increaseView();
        ClubResponse clubResponse = ClubResponse.from(club, null);
        List<Announcement> announcements = announcementRepository.findByClub(club);
        AnnouncementResponse announcementResponse;
        if (announcements.isEmpty()) {
            announcementResponse = null;
        } else {
            announcementResponse = AnnouncementResponse.from(announcements.get(announcements.size() - 1));
        }
        List<Belong> belongs = belongRepository.findByClub(club);
        List<ReviewResponse> reviewResponses = new ArrayList<>();
        for (Belong belong : belongs) {
            if (belong.getReview() != null) {
                reviewResponses.add(ReviewResponse.from(belong));
            }
        }
        List<QnaQuestion> qnaQuestions = qnaQuestionRepository.findByClub(club);
        List<QnaResponse> qnaResponses = new ArrayList<>();
        for (QnaQuestion qnaQuestion : qnaQuestions) {
            List<QnaAnswer> qnaAnswers = qnaAnswerRepository.findByQnaQuestion(qnaQuestion);
            QnaResponse response = new QnaResponse();
            response.from(qnaQuestion, qnaAnswers);
            qnaResponses.add(response);
        }
        return ShowClubInfoResponse.from(clubResponse, announcementResponse, reviewResponses, qnaResponses);
    }

    public void updateClubInfo(UpdateClubRequest request, MultipartFile multipartFile) {
        Club club = clubRepository.findById(request.getClubId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.CLUB_NOT_FOUND));
        if (!club.getName().equals(request.getName()) && clubRepository.existsByName(request.getName())) {
            throw new BadRequestException(ErrorCode.DUPLICATE_CLUB);
        }
        if (request.getIsImageChanged()) {
            String imageUrl = null;
            try {
                if (multipartFile != null && !multipartFile.isEmpty())
                    imageUrl = s3Service.uploadFiles(multipartFile, "images");
                if (club.getImageUrl() != null)
                    s3Service.deleteS3(URLDecoder.decode(club.getImageUrl().substring(50), "UTF-8"));
            } catch (Exception e) {
                throw new BadRequestException(ErrorCode.S3_CONNECTION_ERROR);
            }
            club.updateImageUrl(imageUrl);
        }
        club.updateName(request.getName());
        club.updateCategory(request.getCategory());
        club.updateArea(request.getArea());
        club.updateIntroduction(request.getIntroduction());
    }

    private List<ClubResponse> mappingShowClubResponse(List<Club> clubs) {
        List<ClubResponse> responses = new ArrayList<>();

        for (Club club : clubs) {
            List<Announcement> announcements = announcementRepository.findByClub(club);
            if (announcements.isEmpty()) {
                responses.add(ClubResponse.from(club, null));
            } else {
                responses.add(ClubResponse.from(club, announcements.get(announcements.size() - 1).getEndDate()));
            }
        }
        return responses;
    }
}