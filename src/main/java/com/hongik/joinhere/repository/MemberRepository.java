package com.hongik.joinhere.repository;

import com.hongik.joinhere.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

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
