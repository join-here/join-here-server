package com.hongik.joinhere.global.common.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/** 상속받으면 createdAt, updatedAt 필드 자동으로 만들어주는 엔티티
 * jpa의 audit(감시) 기능을 사용합니다 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class TimeBaseEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column
    private LocalDateTime updatedAt;
}