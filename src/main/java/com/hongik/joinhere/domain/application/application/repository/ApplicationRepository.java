package com.hongik.joinhere.domain.application.application.repository;

import com.hongik.joinhere.domain.announcement.announcement.entity.Announcement;
import com.hongik.joinhere.domain.application.application.entity.Application;
import com.hongik.joinhere.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByAnnouncement(Announcement announcement);
    List<Application> findByMember(Member member);
}
