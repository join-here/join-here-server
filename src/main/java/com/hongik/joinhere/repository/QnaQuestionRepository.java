package com.hongik.joinhere.repository;

import com.hongik.joinhere.entity.QnaQuestion;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class QnaQuestionRepository {

    @PersistenceContext
    EntityManager em;

    public void save(QnaQuestion qnaQuestion) {
        em.persist(qnaQuestion);
    }

    public QnaQuestion findById(Long id) {
        return em.find(QnaQuestion.class, id);
    }

    public List<QnaQuestion> findByClubId(Long clubId) {
        return em.createQuery("select q from QnaQuestion q where q.club.id = :club", QnaQuestion.class)
                .setParameter("club", clubId)
                .getResultList();
    }

    public void delete(QnaQuestion qnaQuestion) {
        em.remove(qnaQuestion);
    }
}
