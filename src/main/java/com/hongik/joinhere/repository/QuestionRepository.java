package com.hongik.joinhere.repository;

import com.hongik.joinhere.entity.Question;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class QuestionRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Question question) {
        em.persist(question);
    }
}
