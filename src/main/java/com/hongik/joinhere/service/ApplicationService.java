package com.hongik.joinhere.service;

import com.hongik.joinhere.dto.application.ShowApplicantResponse;
import com.hongik.joinhere.dto.application.ShowApplicationResponse;
import com.hongik.joinhere.dto.application.UpdateApplicantRequest;
import com.hongik.joinhere.entity.Announcement;
import com.hongik.joinhere.entity.Answer;
import com.hongik.joinhere.entity.Application;
import com.hongik.joinhere.entity.Question;
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

    @Autowired
    public ApplicationService(AnnouncementRepository announcementRepository, ApplicationRepository applicationRepository, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.announcementRepository = announcementRepository;
        this.applicationRepository = applicationRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
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
