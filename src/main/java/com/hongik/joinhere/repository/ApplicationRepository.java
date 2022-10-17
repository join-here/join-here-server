package com.hongik.joinhere.repository;

import com.hongik.joinhere.entity.Application;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ApplicationRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Application application) {
        em.persist(application);
    }
}
