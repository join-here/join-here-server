package com.hongik.joinhere.service;

import com.hongik.joinhere.dto.announcement.CreateAnnouncementRequest;
import com.hongik.joinhere.dto.announcement.CreateAnnouncementResponse;
import com.hongik.joinhere.entity.Announcement;
import com.hongik.joinhere.entity.Club;
import com.hongik.joinhere.entity.Question;
import com.hongik.joinhere.repository.AnnouncementRepository;
import com.hongik.joinhere.repository.ClubRepository;
import com.hongik.joinhere.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final ClubRepository clubRepository;
    private final QuestionRepository questionRepository;

    public CreateAnnouncementResponse register(CreateAnnouncementRequest request, Long clubId) {
        Club club = clubRepository.findById(clubId);
        Announcement announcement = request.toAnnouncement(club);
        CreateAnnouncementResponse response = CreateAnnouncementResponse.from(announcementRepository.save(announcement));
        for (String content : request.getQuestion()) {
            Question question = new Question(null, content, announcement);
            questionRepository.save(question);
        }
        return response;
    }
}
