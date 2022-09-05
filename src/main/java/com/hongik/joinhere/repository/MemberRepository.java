package com.hongik.joinhere.repository;

import com.hongik.joinhere.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

//    @Autowired
//    public MemberRepository(EntityManager em) {
//        this.em = em;
//    }

    public Member save(Member member) {
        em.persist(member);
        return member;
    }
}
