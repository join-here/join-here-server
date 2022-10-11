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

    public Belong findById(Long id) {
        return em.find(Belong.class, id);
    }

    public List<Belong> findByMemberId(Member member) {
        return em.createQuery("select b from Belong b where b.member = :member", Belong.class)
                .setParameter("member", member)
                .getResultList();
    }

    public List<Belong> findByClubId(Long clubId) {
        return em.createQuery("select b from Belong b where b.club.id = :club", Belong.class)
                .setParameter("club", clubId)
                .getResultList();
    }

    public void delete(Belong belong) {
        em.remove(belong);
    }
}
