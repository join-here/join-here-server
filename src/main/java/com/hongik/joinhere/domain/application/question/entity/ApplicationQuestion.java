package com.hongik.joinhere.domain.application.question.entity;

import com.hongik.joinhere.domain.announcement.entity.Announcement;
import com.hongik.joinhere.global.common.entity.TimeBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "application_question")
public class ApplicationQuestion extends TimeBaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String content;

    @ManyToOne
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;
}
