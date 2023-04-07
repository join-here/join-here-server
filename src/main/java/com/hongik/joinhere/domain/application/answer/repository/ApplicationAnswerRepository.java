package com.hongik.joinhere.domain.application.answer.repository;

import com.hongik.joinhere.domain.application.answer.entity.ApplicationAnswer;
import com.hongik.joinhere.domain.application.question.entity.ApplicationQuestion;
import com.hongik.joinhere.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationAnswerRepository extends JpaRepository<ApplicationAnswer, Long> {

    Optional<ApplicationAnswer> findByMemberAndApplicationQuestion(Member member, ApplicationQuestion applicationQuestion);
}
