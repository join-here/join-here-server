package com.hongik.joinhere.domain.club.repository;

import com.hongik.joinhere.domain.club.entity.Category;
import com.hongik.joinhere.domain.club.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {

    boolean existsByName(String name);
    List<Club> findByNameContaining(String name);
    List<Club> findByCategory(Category category);
}
