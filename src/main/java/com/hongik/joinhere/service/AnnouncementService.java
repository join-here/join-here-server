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
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final ClubRepository clubRepository;
    private final QuestionRepository questionRepository;
    private final S3Service s3Service;

    public CreateAnnouncementResponse register(CreateAnnouncementRequest request, MultipartFile multipartFile, Long clubId) {
        String posterUrl = null;
        try {
            if (!multipartFile.isEmpty())
                posterUrl = s3Service.uploadFiles(multipartFile, "images");
        } catch (Exception e) {
            return null;
        }
        Club club = clubRepository.findById(clubId);
        Announcement announcement = request.toAnnouncement(club, posterUrl);
        CreateAnnouncementResponse response = CreateAnnouncementResponse.from(announcementRepository.save(announcement));
        for (String content : request.getQuestion()) {
            Question question = new Question(null, content, announcement);
            questionRepository.save(question);
        }
        return response;
    }
}
