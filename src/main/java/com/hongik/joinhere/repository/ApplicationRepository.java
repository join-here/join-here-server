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

    public List<Application> findByAnnouncementId(Long announcementId) {
        return em.createQuery("select a from Application a where a.announcement.id = :announcement", Application.class)
                .setParameter("announcement", announcementId)
                .getResultList();
    }
}
