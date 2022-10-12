package com.hongik.joinhere.service;

import com.hongik.joinhere.dto.answer.CreateAnswerRequest;
import com.hongik.joinhere.entity.*;
import com.hongik.joinhere.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, MemberRepository memberRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.memberRepository = memberRepository;
        this.questionRepository = questionRepository;
    }

    public void register(List<CreateAnswerRequest> requests) {
        Member member = memberRepository.findById(requests.get(0).getMemberId());
        for (CreateAnswerRequest request : requests) {
            Question question = questionRepository.findById(request.getQuestionId());
            Answer answer = request.toEntity(member, question);
            answerRepository.save(answer);
        }
    }
}
