package com.hongik.joinhere.domain.application.answer;

import com.hongik.joinhere.domain.announcement.entity.Announcement;
import com.hongik.joinhere.domain.announcement.repository.AnnouncementRepository;
import com.hongik.joinhere.domain.application.answer.entity.ApplicationAnswer;
import com.hongik.joinhere.domain.application.answer.repository.ApplicationAnswerRepository;
import com.hongik.joinhere.domain.application.application.entity.PassState;
import com.hongik.joinhere.domain.application.application.repository.ApplicationRepository;
import com.hongik.joinhere.domain.application.question.entity.ApplicationQuestion;
import com.hongik.joinhere.domain.application.application.entity.Application;
import com.hongik.joinhere.domain.application.answer.dto.CreateApplicationAnswerRequest;
import com.hongik.joinhere.domain.application.question.repository.ApplicationQuestionRepository;
import com.hongik.joinhere.domain.auth.security.SecurityUtil;
import com.hongik.joinhere.domain.member.entity.Member;
import com.hongik.joinhere.domain.member.repository.MemberRepository;
import com.hongik.joinhere.global.error.ErrorCode;
import com.hongik.joinhere.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationAnswerService {

    private final MemberRepository memberRepository;
    private final AnnouncementRepository announcementRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationAnswerRepository applicationAnswerRepository;
    private final ApplicationQuestionRepository applicationQuestionRepository;

    public void register(List<CreateApplicationAnswerRequest> requests, Long announcementId) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.ANNOUNCEMENT_NOT_FOUND));
        LocalDate localDate = LocalDate.now(ZoneId.of("Asia/Seoul"));
        if (localDate.isAfter(announcement.getEndDate())) {
            throw new BadRequestException(ErrorCode.APPLICATION_PERIOD_EXPIRED);
        }
        Application application = Application.builder()
                                .passState(PassState.HOLD)
                                .member(member)
                                .announcement(announcement)
                                .build();
        applicationRepository.save(application);
        for (CreateApplicationAnswerRequest request : requests) {
            ApplicationQuestion applicationQuestion = applicationQuestionRepository.findById(request.getQuestionId())
                    .orElseThrow(() -> new BadRequestException(ErrorCode.APPLICATION_QUESTION_NOT_FOUND));
            ApplicationAnswer applicationAnswer = request.toEntity(member, applicationQuestion);
            applicationAnswerRepository.save(applicationAnswer);
        }
    }
}
