package com.hongik.joinhere.repository;

import com.hongik.joinhere.entity.Announcement;
import com.hongik.joinhere.entity.Club;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AnnouncementRepository {

    @PersistenceContext
    private EntityManager em;

    public Announcement save(Announcement announcement) {
        em.persist(announcement);
        return announcement;
    }

    public List<Announcement> findByClubId(Long id) {
        return em.createQuery("select a from Announcement a where a.club.id = :club", Announcement.class)
                .setParameter("club", id)
                .getResultList();
    }
}
