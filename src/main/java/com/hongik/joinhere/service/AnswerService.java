package com.hongik.joinhere.service;

import com.hongik.joinhere.dto.answer.CreateAnswerRequest;
import com.hongik.joinhere.entity.*;
import com.hongik.joinhere.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;
    private final AnnouncementRepository announcementRepository;
    private final ApplicationRepository applicationRepository;

    public void register(List<CreateAnswerRequest> requests, Long announcementId) {
        Member member = memberRepository.findById(requests.get(0).getMemberId());
        Announcement announcement = announcementRepository.findById(announcementId);
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        Application application = new Application(null, "hold", "n", now ,member, announcement);
        applicationRepository.save(application);
        for (CreateAnswerRequest request : requests) {
            Question question = questionRepository.findById(request.getQuestionId());
            Answer answer = request.toEntity(member, question);
            answerRepository.save(answer);
        }
    }
}
