package com.hongik.joinhere.repository;

import com.hongik.joinhere.entity.Club;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ClubRepository {

    @PersistenceContext
    private EntityManager em;

    public Club save(Club club) {
        em.persist(club);
        return club;
    }
}
