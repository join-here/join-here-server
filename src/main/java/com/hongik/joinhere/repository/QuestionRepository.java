package com.hongik.joinhere.repository;

import com.hongik.joinhere.entity.Question;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class QuestionRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Question question) {
        em.persist(question);
    }

    public Question findById(Long id) {
        return em.find(Question.class, id);
    }

    public List<Question> findByAnnouncementId(Long announcementId) {
        return em.createQuery("select q from Question q where q.announcement.id = :announcement", Question.class)
                .setParameter("announcement", announcementId)
                .getResultList();
    }
}
