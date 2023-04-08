package com.hongik.joinhere.domain.qna.answer.repository;

import com.hongik.joinhere.domain.qna.answer.entity.QnaAnswer;
import com.hongik.joinhere.domain.qna.question.entity.QnaQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaAnswerRepository extends JpaRepository<QnaAnswer, Long> {

    List<QnaAnswer> findByQnaQuestion(QnaQuestion qnaQuestion);
}
