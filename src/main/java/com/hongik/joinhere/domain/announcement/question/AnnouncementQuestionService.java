package com.hongik.joinhere.domain.announcement.question;

import com.hongik.joinhere.domain.announcement.announcement.entity.Announcement;
import com.hongik.joinhere.domain.announcement.announcement.repository.AnnouncementRepository;
import com.hongik.joinhere.domain.announcement.question.entity.AnnouncementQuestion;
import com.hongik.joinhere.domain.announcement.question.dto.ShowAnnouncementQuestionResponse;
import com.hongik.joinhere.domain.announcement.question.repository.AnnouncementQuestionRepository;
import com.hongik.joinhere.global.error.ErrorCode;
import com.hongik.joinhere.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AnnouncementQuestionService {

    private final AnnouncementQuestionRepository announcementQuestionRepository;
    private final AnnouncementRepository announcementRepository;

    public List<ShowAnnouncementQuestionResponse> findApplicationQuestionsByAnnouncement(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.ANNOUNCEMENT_NOT_FOUND));
        LocalDate localDate = LocalDate.now(ZoneId.of("Asia/Seoul"));
        if (localDate.isAfter(announcement.getEndDate())) {
            throw new BadRequestException(ErrorCode.APPLICATION_PERIOD_EXPIRED);
        }
        List<AnnouncementQuestion> announcementQuestions = announcementQuestionRepository.findByAnnouncement(announcement);
        List<ShowAnnouncementQuestionResponse> responses = new ArrayList<>();
        for (AnnouncementQuestion announcementQuestion : announcementQuestions) {
            responses.add(ShowAnnouncementQuestionResponse.from(announcementQuestion));
        }
        return responses;
    }
}
