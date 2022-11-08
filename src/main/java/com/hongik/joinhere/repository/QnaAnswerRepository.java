package com.hongik.joinhere.repository;

import com.hongik.joinhere.entity.QnaAnswer;
import com.hongik.joinhere.entity.QnaQuestion;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class QnaAnswerRepository {

    @PersistenceContext
    EntityManager em;

    public void save(QnaAnswer qnaAnswer) {
        em.persist(qnaAnswer);
    }

    public List<QnaAnswer> findByQnaQuestionId(Long qnaQuestionId) {
        return em.createQuery("select q from QnaAnswer q where q.qnaQuestion.id = :qnaQuestion", QnaAnswer.class)
                .setParameter("qnaQuestion", qnaQuestionId)
                .getResultList();
    }
}
