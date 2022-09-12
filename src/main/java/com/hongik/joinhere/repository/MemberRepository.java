package com.hongik.joinhere.repository;

import com.hongik.joinhere.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    public Member findById(String id) {
        Member member = em.find(Member.class, id);
        return member;
    }
}
