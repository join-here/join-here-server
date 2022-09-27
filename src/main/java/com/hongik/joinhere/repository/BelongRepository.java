package com.hongik.joinhere.repository;

import com.hongik.joinhere.entity.Belong;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BelongRepository {

    @PersistenceContext
    private EntityManager em;

    public Belong save(Belong belong) {
        em.persist(belong);
        return belong;
    }
}
