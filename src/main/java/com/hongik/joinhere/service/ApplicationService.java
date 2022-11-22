package com.hongik.joinhere.service;

import com.hongik.joinhere.dto.application.*;
import com.hongik.joinhere.entity.*;
import com.hongik.joinhere.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationService {

    private final AnnouncementRepository announcementRepository;
    private final ApplicationRepository applicationRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;
    private final BelongRepository belongRepository;

    public List<ShowApplicantResponse> findApplicants(Long clubId) {
        List<Announcement> announcements = announcementRepository.findByClubId(clubId);
        Announcement announcement = announcements.get(announcements.size() - 1);
        List<Application> applications = applicationRepository.findByAnnouncementId(announcement.getId());
        List<ShowApplicantResponse> responses = new ArrayList<>();
        for (Application application : applications)
            responses.add(ShowApplicantResponse.from(application));
        return responses;
    }

    public List<ShowApplicantResponse> updateApplicantsPassState(List<UpdateApplicantRequest> requests) {
        List<ShowApplicantResponse> responses = new ArrayList<>();
        for (UpdateApplicantRequest request : requests) {
            Application application = applicationRepository.findById(request.getApplicationId());
            application.setPassState(request.getPassState());
            responses.add(ShowApplicantResponse.from(application));
        }
        return responses;
    }

    public void publishApplications(List<PublishApplicationRequest> requests, Long clubId) {
        Club club = clubRepository.findById(clubId);
        List<Announcement> announcements = announcementRepository.findByClubId(clubId);
        announcements.get(announcements.size() - 1).setInformState("y");
        for (PublishApplicationRequest request : requests) {
            Application application = applicationRepository.findById(request.getApplicationId());
            application.setInformState("y");
            if (request.getPassState().equals("pass")) {
                application.setPassState(request.getPassState());
                Member member = memberRepository.findById(request.getMemberId());
                Belong belong = new Belong(null, "nor", member, club);
                belongRepository.save(belong);
            }
            else
                application.setPassState("fail");
        }
    }

    public List<ShowApplicationContentResponse> findApplications(String memberId, Long applicationId) {
        Application application = applicationRepository.findById(applicationId);
        Announcement announcement = announcementRepository.findById(application.getAnnouncement().getId());
        List<Question> questions = questionRepository.findByAnnouncementId(announcement.getId());
        List<ShowApplicationContentResponse> responses = new ArrayList<>();
        for (Question question : questions) {
            Answer answer = answerRepository.findByMemberIdAndQuestionId(memberId, question.getId()).get(0);
            responses.add(ShowApplicationContentResponse.from(question, answer));
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
}
