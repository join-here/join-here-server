package com.hongik.joinhere.domain.qna.question.repository;

import com.hongik.joinhere.domain.club.entity.Club;
import com.hongik.joinhere.domain.qna.question.entity.QnaQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaQuestionRepository extends JpaRepository<QnaQuestion, Long> {

    List<QnaQuestion> findByClub(Club club);
}
