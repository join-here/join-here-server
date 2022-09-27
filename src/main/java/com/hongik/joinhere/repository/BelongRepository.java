package com.hongik.joinhere.repository;

import com.hongik.joinhere.entity.Belong;
import com.hongik.joinhere.entity.Club;
import com.hongik.joinhere.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BelongRepository {

    @PersistenceContext
    private EntityManager em;

    public Belong save(Belong belong) {
        em.persist(belong);
        return belong;
    }

    public List<Belong> findByMemberId(Member member) {
        return em.createQuery("select b from Belong b where b.member = :member", Belong.class)
                .setParameter("member", member)
                .getResultList();
    }
}
