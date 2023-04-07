package com.hongik.joinhere.domain.application.question.repository;

import com.hongik.joinhere.domain.announcement.entity.Announcement;
import com.hongik.joinhere.domain.application.question.entity.ApplicationQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationQuestionRepository extends JpaRepository<ApplicationQuestion, Long> {

    List<ApplicationQuestion> findByAnnouncement(Announcement announcement);
}
