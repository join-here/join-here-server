package com.hongik.joinhere.service;

import com.hongik.joinhere.dto.answer.CreateAnswerRequest;
import com.hongik.joinhere.entity.*;
import com.hongik.joinhere.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;
    private final AnnouncementRepository announcementRepository;
    private final ApplicationRepository applicationRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, MemberRepository memberRepository, QuestionRepository questionRepository, AnnouncementRepository announcementRepository, ApplicationRepository applicationRepository) {
        this.answerRepository = answerRepository;
        this.memberRepository = memberRepository;
        this.questionRepository = questionRepository;
        this.announcementRepository = announcementRepository;
        this.applicationRepository = applicationRepository;
    }

    public void register(List<CreateAnswerRequest> requests, Long announcementId) {
        Member member = memberRepository.findById(requests.get(0).getMemberId());
        Announcement announcement = announcementRepository.findById(announcementId);
        Application application = new Application(null, "hold", "n", member, announcement);
        applicationRepository.save(application);
        for (CreateAnswerRequest request : requests) {
            Question question = questionRepository.findById(request.getQuestionId());
            Answer answer = request.toEntity(member, question);
            answerRepository.save(answer);
        }
    }
}
