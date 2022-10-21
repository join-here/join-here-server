package com.hongik.joinhere.service;

import com.hongik.joinhere.dto.application.PublishApplicationRequest;
import com.hongik.joinhere.dto.application.ShowApplicantResponse;
import com.hongik.joinhere.dto.application.ShowApplicationResponse;
import com.hongik.joinhere.dto.application.UpdateApplicantRequest;
import com.hongik.joinhere.entity.*;
import com.hongik.joinhere.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ApplicationService {

    private final AnnouncementRepository announcementRepository;
    private final ApplicationRepository applicationRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;
    private final BelongRepository belongRepository;

    @Autowired
    public ApplicationService(AnnouncementRepository announcementRepository, ApplicationRepository applicationRepository, QuestionRepository questionRepository, AnswerRepository answerRepository, ClubRepository clubRepository, MemberRepository memberRepository, BelongRepository belongRepository) {
        this.announcementRepository = announcementRepository;
        this.applicationRepository = applicationRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.clubRepository = clubRepository;
        this.memberRepository = memberRepository;
        this.belongRepository = belongRepository;
    }

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

    public List<ShowApplicationResponse> findApplications(String memberId, Long applicationId) {
        Application application = applicationRepository.findById(applicationId);
        Announcement announcement = announcementRepository.findById(application.getAnnouncement().getId());
        List<Question> questions = questionRepository.findByAnnouncementId(announcement.getId());
        List<ShowApplicationResponse> responses = new ArrayList<>();
        for (Question question : questions) {
            Answer answer = answerRepository.findByMemberIdAndQuestionId(memberId, question.getId()).get(0);
            responses.add(ShowApplicationResponse.from(question, answer));
        }
        return responses;
    }
}
