package com.hongik.joinhere.domain.announcement.question.repository;

import com.hongik.joinhere.domain.announcement.announcement.entity.Announcement;
import com.hongik.joinhere.domain.announcement.question.entity.AnnouncementQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementQuestionRepository extends JpaRepository<AnnouncementQuestion, Long> {

    List<AnnouncementQuestion> findByAnnouncement(Announcement announcement);
}
