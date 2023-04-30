package com.hongik.joinhere.domain.application.answer.entity;

import com.hongik.joinhere.domain.application.application.entity.Application;
import com.hongik.joinhere.domain.announcement.question.entity.AnnouncementQuestion;
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
@Table(name = "application_answer")
public class ApplicationAnswer extends TimeBaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "application_id")
    Application application;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "application_question_id")
    AnnouncementQuestion announcementQuestion;
}
