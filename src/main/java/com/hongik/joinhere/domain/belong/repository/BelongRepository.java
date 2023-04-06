package com.hongik.joinhere.domain.belong.repository;

import com.hongik.joinhere.domain.belong.entity.Belong;
import com.hongik.joinhere.domain.club.entity.Club;
import com.hongik.joinhere.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BelongRepository extends JpaRepository<Belong, Long> {

    Optional<Belong> findByMemberAndClub(Member member, Club club);
    List<Belong> findByClub(Club club);
}
