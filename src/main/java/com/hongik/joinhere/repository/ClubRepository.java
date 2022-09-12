package com.hongik.joinhere.repository;

import com.hongik.joinhere.entity.Club;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ClubRepository {

    @PersistenceContext
    private EntityManager em;

    public Club save(Club club) {
        em.persist(club);
        return club;
    }

    public List<Club> findAll() {
        return em.createQuery("select c from Club c", Club.class)
                .getResultList();
    }

    public List<Club> findByCategory(String category) {
        return em.createQuery("select c from Club c where c.category = :category", Club.class)
                .setParameter("category", category)
                .getResultList();
    }
}
