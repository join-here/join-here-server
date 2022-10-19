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

    public Announcement findById(Long id) {
        return em.find(Announcement.class, id);
    }

    public List<Announcement> findByClubId(Long clubId) {
        return em.createQuery("select a from Announcement a where a.club.id = :club", Announcement.class)
                .setParameter("club", clubId)
                .getResultList();
    }
}
