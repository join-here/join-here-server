package com.hongik.joinhere.service;

import com.hongik.joinhere.controller.QnaController;
import com.hongik.joinhere.dto.qna.CreateQnaQuestionRequest;
import com.hongik.joinhere.dto.qna.ShowQnaResponse;
import com.hongik.joinhere.entity.*;
import com.hongik.joinhere.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class QnaService {

    private final QnaQuestionRepository qnaQuestionRepository;
    private final QnaAnswerRepository qnaAnswerRepository;
    private final BelongRepository belongRepository;
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;

    public List<ShowQnaResponse> registerQuestion(CreateQnaQuestionRequest request, Long clubId) {
        Member member = memberRepository.findById(request.getMemberId());
        Club club = clubRepository.findById(clubId);
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        QnaQuestion saveQnaQuestion = new QnaQuestion(null, request.getQuestionContent(), now, member, club);
        qnaQuestionRepository.save(saveQnaQuestion);
        List<QnaQuestion> qnaQuestions = qnaQuestionRepository.findByClubId(clubId);
        List<ShowQnaResponse> responses = new ArrayList<>();

        for (QnaQuestion qnaQuestion : qnaQuestions) {
            List<QnaAnswer> qnaAnswers = qnaAnswerRepository.findByQnaQuestionId(qnaQuestion.getId());
            ShowQnaResponse response = new ShowQnaResponse();
            response.from(qnaQuestion, qnaAnswers);
            responses.add(response);
        }
        return responses;
    }
}