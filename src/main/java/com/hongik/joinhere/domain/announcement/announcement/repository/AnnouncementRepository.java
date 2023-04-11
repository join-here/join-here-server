package com.hongik.joinhere.domain.announcement.announcement.repository;

import com.hongik.joinhere.domain.announcement.announcement.entity.Announcement;
import com.hongik.joinhere.domain.club.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findByClub(Club club);
}
