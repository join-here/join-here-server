package com.hongik.joinhere.repository;

import com.hongik.joinhere.entity.Application;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ApplicationRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Application application) {
        em.persist(application);
    }

    public Application findById(Long id) {
        return em.find(Application.class, id);
    }

    public List<Application> findByAnnouncementId(Long announcementId) {
        return em.createQuery("select a from Application a where a.announcement.id = :announcement", Application.class)
                .setParameter("announcement", announcementId)
                .getResultList();
    }

    public List<Application> findByMemberId(String memberId) {
        return em.createQuery("select a from Application a where a.member.id = :member", Application.class)
                .setParameter("member", memberId)
                .getResultList();
    }
}
