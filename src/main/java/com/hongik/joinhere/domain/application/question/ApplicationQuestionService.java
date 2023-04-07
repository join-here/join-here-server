package com.hongik.joinhere.domain.application.question;

import com.hongik.joinhere.domain.announcement.entity.Announcement;
import com.hongik.joinhere.domain.announcement.repository.AnnouncementRepository;
import com.hongik.joinhere.domain.application.question.entity.ApplicationQuestion;
import com.hongik.joinhere.domain.application.question.dto.ShowApplicationQuestionResponse;
import com.hongik.joinhere.domain.application.question.repository.ApplicationQuestionRepository;
import com.hongik.joinhere.global.error.ErrorCode;
import com.hongik.joinhere.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationQuestionService {

    private final ApplicationQuestionRepository applicationQuestionRepository;
    private final AnnouncementRepository announcementRepository;

    public List<ShowApplicationQuestionResponse> findApplicationQuestionsByAnnouncement(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.ANNOUNCEMENT_NOT_FOUND));
        LocalDate localDate = LocalDate.now();
        if (localDate.isAfter(announcement.getEndDate())) {
            throw new BadRequestException(ErrorCode.APPLICATION_PERIOD_EXPIRED);
        }
        List<ApplicationQuestion> applicationQuestions = applicationQuestionRepository.findByAnnouncement(announcement);
        List<ShowApplicationQuestionResponse> responses = new ArrayList<>();
        for (ApplicationQuestion applicationQuestion : applicationQuestions) {
            responses.add(ShowApplicationQuestionResponse.from(applicationQuestion));
        }
        return responses;
    }
}
