package com.hongik.joinhere.repository;

import com.hongik.joinhere.entity.Club;
import com.hongik.joinhere.entity.Review;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ReviewRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Review review) {
        em.persist(review);
    }

    public Review findById(Long id) {
        return em.find(Review.class, id);
    }

    public List<Review> findByClubId(Long clubId) {
        return em.createQuery("select r from Review r where r.club.id = :club", Review.class)
                    .setParameter("club", clubId)
                    .getResultList();
    }

    public void delete(Review review) {
        em.remove(review);
    }
}
