package com.hongik.joinhere.domain.application.answer.repository;

import com.hongik.joinhere.domain.application.answer.entity.ApplicationAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationAnswerRepository extends JpaRepository<ApplicationAnswer, Long> {
}
