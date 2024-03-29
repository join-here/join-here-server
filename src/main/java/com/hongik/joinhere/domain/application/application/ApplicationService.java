package com.hongik.joinhere.domain.application.application;

import com.hongik.joinhere.domain.announcement.announcement.entity.Announcement;
import com.hongik.joinhere.domain.announcement.announcement.repository.AnnouncementRepository;
import com.hongik.joinhere.domain.application.answer.repository.ApplicationAnswerRepository;
import com.hongik.joinhere.domain.application.application.dto.*;
import com.hongik.joinhere.domain.application.application.entity.Application;
import com.hongik.joinhere.domain.application.application.entity.PassState;
import com.hongik.joinhere.domain.application.application.repository.ApplicationRepository;
import com.hongik.joinhere.domain.announcement.question.entity.AnnouncementQuestion;
import com.hongik.joinhere.domain.application.answer.entity.ApplicationAnswer;
import com.hongik.joinhere.domain.announcement.question.repository.AnnouncementQuestionRepository;
import com.hongik.joinhere.domain.auth.security.SecurityUtil;
import com.hongik.joinhere.domain.belong.entity.Belong;
import com.hongik.joinhere.domain.belong.entity.Position;
import com.hongik.joinhere.domain.belong.repository.BelongRepository;
import com.hongik.joinhere.domain.club.entity.Club;
import com.hongik.joinhere.domain.club.repository.ClubRepository;
import com.hongik.joinhere.domain.member.entity.Member;
import com.hongik.joinhere.domain.member.repository.MemberRepository;
import com.hongik.joinhere.global.error.ErrorCode;
import com.hongik.joinhere.global.error.exception.BadRequestException;
import com.hongik.joinhere.global.error.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationService {

    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;
    private final BelongRepository belongRepository;
    private final AnnouncementRepository announcementRepository;
    private final ApplicationRepository applicationRepository;
    private final AnnouncementQuestionRepository announcementQuestionRepository;
    private final ApplicationAnswerRepository applicationAnswerRepository;

    public List<ShowApplicantResponse> findApplicants(Long clubId) {
        Club club = handleClubManageAuthorityException(clubId);
        List<Announcement> announcements = announcementRepository.findByClub(club);
        List<Application> applications = applicationRepository.findByAnnouncement(announcements.get(announcements.size() - 1));
        List<ShowApplicantResponse> responses = new ArrayList<>();
        for (Application application : applications) {
            responses.add(ShowApplicantResponse.from(application));
        }
        return responses;
    }

    public List<ShowApplicantResponse> updateApplicantsPassState(List<ApplicantRequest> requests, Long clubId) {
        handleClubManageAuthorityException(clubId);
        List<ShowApplicantResponse> responses = new ArrayList<>();
        for (ApplicantRequest request : requests) {
            Application application = applicationRepository.findById(request.getApplicationId())
                            .orElseThrow(() -> new BadRequestException(ErrorCode.APPLICATION_NOT_FOUND));
            application.updatePassState(request.getPassState());
            responses.add(ShowApplicantResponse.from(application));
        }
        return responses;
    }

    public void publishApplications(List<ApplicantRequest> requests, Long clubId) {
        Club club = handleClubManageAuthorityException(clubId);
        List<Announcement> announcements = announcementRepository.findByClub(club);
        announcements.get(announcements.size() - 1).updateInformState(true);
        for (ApplicantRequest request : requests) {
            Application application = applicationRepository.findById(request.getApplicationId())
                            .orElseThrow(() -> new BadRequestException(ErrorCode.APPLICATION_NOT_FOUND));
            if (request.getPassState() == PassState.PASS) {
                application.updatePassState(request.getPassState());
                Belong belong = Belong.builder()
                        .position(Position.NORMAL)
                        .member(application.getMember())
                        .club(club)
                        .build();
                belongRepository.save(belong);
            }
            else {
                application.updatePassState(PassState.FAIL);
            }
        }
    }

    public List<ShowMyApplicationResponse> findMyApplications() {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
        List<Application> applications = applicationRepository.findByMember(member);
        List<ShowMyApplicationResponse> responses = new ArrayList<>();
        for (Application application : applications) {
            responses.add(ShowMyApplicationResponse.from(application.getAnnouncement().getClub(), application));
        }
        return responses;
    }

    public List<ShowApplicationContentResponse> findApplications(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).
                orElseThrow(() -> new BadRequestException(ErrorCode.APPLICATION_NOT_FOUND));
        handleClubManageAuthorityException(application.getAnnouncement().getClub().getId());
        Announcement announcement = application.getAnnouncement();
        List<AnnouncementQuestion> announcementQuestions = announcementQuestionRepository.findByAnnouncement(announcement);
        List<ShowApplicationContentResponse> responses = new ArrayList<>();
        for (AnnouncementQuestion announcementQuestion : announcementQuestions) {
            ApplicationAnswer applicationAnswer = applicationAnswerRepository.findByApplicationAndAnnouncementQuestion(application, announcementQuestion)
                            .orElseThrow(() -> new BadRequestException(ErrorCode.APPLICATION_ANSWER_NOT_FOUND));
            responses.add(ShowApplicationContentResponse.from(announcementQuestion, applicationAnswer));
        }
        return responses;
    }

    private Club handleClubManageAuthorityException(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.CLUB_NOT_FOUND));
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
        Belong belong = belongRepository.findByMemberAndClub(member, club)
                .orElseThrow(() -> new BadRequestException(ErrorCode.BELONG_NOT_FOUND));
        if (belong.getPosition() == Position.NORMAL) {
            throw new ForbiddenException(ErrorCode.BELONG_FORBIDDEN_MEMBER);
        }
        return club;
    }
}
