package com.hongik.joinhere.domain.application.application.entity;

import com.hongik.joinhere.domain.announcement.announcement.entity.Announcement;
import com.hongik.joinhere.domain.member.entity.Member;
import com.hongik.joinhere.global.common.entity.TimeBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Application extends TimeBaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pass_state", columnDefinition = "varchar(4) default 'HOLD'")
    private PassState passState;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "announcement_id")
    Announcement announcement;

    public void updatePassState(PassState passState) {
        this.passState = passState;
    }
}
