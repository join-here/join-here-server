package com.hongik.joinhere.repository;

import com.hongik.joinhere.entity.Answer;
import com.hongik.joinhere.entity.Question;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AnswerRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Answer answer) {
        em.persist(answer);
    }

    public List<Answer> findByMemberIdAndQuestionId(String memberId, Long questionId) {
        return em.createQuery("select a from Answer a where a.member.id = :member and a.question.id = :question", Answer.class)
                .setParameter("member", memberId)
                .setParameter("question", questionId)
                .getResultList();
    }
}
