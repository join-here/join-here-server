package com.hongik.joinhere.domain.belong.entity;

import com.hongik.joinhere.domain.club.entity.Club;
import com.hongik.joinhere.domain.member.entity.Member;
import com.hongik.joinhere.global.common.entity.TimeBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Belong extends TimeBaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(9) default 'NORMAL'")
    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(length = 1000)
    private String review;

    @Column(name = "review_created_at")
    private LocalDateTime reviewCreatedAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    public void updatePosition(Position position) {
        this.position = position;
    }

    public void updateReview(String review) {
        this.review = review;
    }

    public void updateReviewCreatedAt(LocalDateTime reviewCreatedAt) {
        this.reviewCreatedAt = reviewCreatedAt;
    }

    public void deleteReview() {
        review = null;
        reviewCreatedAt = null;
    }
}
