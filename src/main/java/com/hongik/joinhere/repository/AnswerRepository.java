package com.hongik.joinhere.repository;

import com.hongik.joinhere.entity.Answer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class AnswerRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Answer answer) {
        em.persist(answer);
    }
}
