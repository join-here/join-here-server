package com.hongik.joinhere.service;

import com.hongik.joinhere.dto.application.ShowApplicationResponse;
import com.hongik.joinhere.entity.Announcement;
import com.hongik.joinhere.entity.Application;
import com.hongik.joinhere.entity.Club;
import com.hongik.joinhere.repository.AnnouncementRepository;
import com.hongik.joinhere.repository.ApplicationRepository;
import com.hongik.joinhere.repository.ClubRepository;
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

    @Autowired
    public ApplicationService(AnnouncementRepository announcementRepository, ApplicationRepository applicationRepository) {
        this.announcementRepository = announcementRepository;
        this.applicationRepository = applicationRepository;
    }

    public List<ShowApplicationResponse> findApplications(Long clubId) {
        List<Announcement> announcements = announcementRepository.findByClubId(clubId);
        Announcement announcement = announcements.get(announcements.size() - 1);
        List<Application> applications = applicationRepository.findByAnnouncementId(announcement.getId());
        List<ShowApplicationResponse> responses = new ArrayList<>();
        for (Application application : applications)
            responses.add(ShowApplicationResponse.from(application));
        return responses;
    }
}
