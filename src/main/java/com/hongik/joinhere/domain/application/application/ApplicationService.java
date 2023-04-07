package com.hongik.joinhere.domain.application.application;

import com.hongik.joinhere.domain.announcement.entity.Announcement;
import com.hongik.joinhere.domain.announcement.repository.AnnouncementRepository;
import com.hongik.joinhere.domain.application.application.dto.PublishApplicationRequest;
import com.hongik.joinhere.domain.application.application.dto.ShowApplicantResponse;
import com.hongik.joinhere.domain.application.application.dto.UpdateApplicantRequest;
import com.hongik.joinhere.domain.application.application.entity.Application;
import com.hongik.joinhere.domain.application.application.entity.PassState;
import com.hongik.joinhere.domain.application.application.repository.ApplicationRepository;
import com.hongik.joinhere.domain.application.question.entity.ApplicationQuestion;
import com.hongik.joinhere.domain.application.answer.entity.ApplicationAnswer;
import com.hongik.joinhere.domain.auth.security.SecurityUtil;
import com.hongik.joinhere.domain.belong.entity.Belong;
import com.hongik.joinhere.domain.belong.entity.Position;
import com.hongik.joinhere.domain.belong.repository.BelongRepository;
import com.hongik.joinhere.domain.club.entity.Club;
import com.hongik.joinhere.domain.club.repository.ClubRepository;
import com.hongik.joinhere.domain.dto.application.*;
import com.hongik.joinhere.domain.entity.*;
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
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;


    public List<ShowApplicantResponse> findApplicants(Long clubId) {
        Club club = checkClubManageAuthority(clubId);
        List<Announcement> announcements = announcementRepository.findByClub(club);
        Announcement announcement = announcements.get(announcements.size() - 1);
        List<Application> applications = applicationRepository.findByAnnouncement(announcement);
        List<ShowApplicantResponse> responses = new ArrayList<>();
        for (Application application : applications)
            responses.add(ShowApplicantResponse.from(application));
        return responses;
    }

    public List<ShowApplicantResponse> updateApplicantsPassState(Long clubId, List<UpdateApplicantRequest> requests) {
        checkClubManageAuthority(clubId);
        List<ShowApplicantResponse> responses = new ArrayList<>();
        for (UpdateApplicantRequest request : requests) {
            Application application = applicationRepository.findById(request.getApplicationId())
                            .orElseThrow(() -> new BadRequestException(ErrorCode.APPLICATION_NOT_FOUND));
            application.updatePassState(request.getPassState());
            responses.add(ShowApplicantResponse.from(application));
        }
        return responses;
    }

    public void publishApplications(List<PublishApplicationRequest> requests, Long clubId) {
        Club club = checkClubManageAuthority(clubId);
        List<Announcement> announcements = announcementRepository.findByClub(club);
        announcements.get(announcements.size() - 1).updateInformState(true);
        for (PublishApplicationRequest request : requests) {
            Application application = applicationRepository.findById(request.getApplicationId())
                            .orElseThrow(() -> new BadRequestException(ErrorCode.APPLICATION_NOT_FOUND));
            if (request.getPassState() == PassState.PASS) {
                application.updatePassState(request.getPassState());
                Member member = memberRepository.findById(request.getMemberId())
                        .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
                Belong belong = Belong.builder()
                        .position(Position.NORMAL)
                        .member(member)
                        .club(club)
                        .build();
                belongRepository.save(belong);
            }
            else {
                application.updatePassState(PassState.FAIL);
            }
        }
    }

    public List<ShowApplicationContentResponse> findApplications(Long applicationId) {
        Application application = applicationRepository.findById(applicationId);
        Announcement announcement = announcementRepository.findById(application.getAnnouncement().getId());
        List<ApplicationQuestion> applicationQuestions = questionRepository.findByAnnouncementId(announcement.getId());
        List<ShowApplicationContentResponse> responses = new ArrayList<>();
        for (ApplicationQuestion applicationQuestion : applicationQuestions) {
            ApplicationAnswer applicationAnswer = answerRepository.findByMemberIdAndQuestionId(application.getMember().getId(), applicationQuestion.getId()).get(0);
            responses.add(ShowApplicationContentResponse.from(applicationQuestion, applicationAnswer));
        }
        return responses;
    }

    public List<ShowMyApplicationResponse> findApplicationsByMemberId(String memberId) {
        List<Application> applications = applicationRepository.findByMemberId(memberId);
        List<ShowMyApplicationResponse> responses = new ArrayList<>();
        for (Application application : applications) {
            Club club = clubRepository.findById(application.getAnnouncement().getClub().getId());
            responses.add(ShowMyApplicationResponse.from(club, application));
        }
        return responses;
    }

    private Club checkClubManageAuthority(Long clubId) {
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
