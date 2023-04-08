package com.hongik.joinhere.domain.qna.qna;

import com.hongik.joinhere.domain.auth.security.SecurityUtil;
import com.hongik.joinhere.domain.belong.entity.Belong;
import com.hongik.joinhere.domain.belong.entity.Position;
import com.hongik.joinhere.domain.belong.repository.BelongRepository;
import com.hongik.joinhere.domain.club.entity.Club;
import com.hongik.joinhere.domain.club.repository.ClubRepository;
import com.hongik.joinhere.domain.member.entity.Member;
import com.hongik.joinhere.domain.member.repository.MemberRepository;
import com.hongik.joinhere.domain.qna.answer.dto.CreateQnaAnswerRequest;
import com.hongik.joinhere.domain.qna.answer.dto.DeleteQnaAnswerRequest;
import com.hongik.joinhere.domain.qna.answer.repository.QnaAnswerRepository;
import com.hongik.joinhere.domain.qna.qna.dto.QnaResponse;
import com.hongik.joinhere.domain.qna.question.dto.CreateQnaQuestionRequest;
import com.hongik.joinhere.domain.qna.question.dto.DeleteQnaQuestionRequest;
import com.hongik.joinhere.domain.qna.answer.entity.QnaAnswer;
import com.hongik.joinhere.domain.qna.question.entity.QnaQuestion;
import com.hongik.joinhere.domain.qna.question.repository.QnaQuestionRepository;
import com.hongik.joinhere.global.error.ErrorCode;
import com.hongik.joinhere.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class QnaService {

    private final QnaQuestionRepository qnaQuestionRepository;
    private final QnaAnswerRepository qnaAnswerRepository;
    private final BelongRepository belongRepository;
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;

    public List<QnaResponse> registerQuestion(CreateQnaQuestionRequest request, Long clubId) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.CLUB_NOT_FOUND));
        QnaQuestion qnaQuestion = QnaQuestion.builder()
                                .content(request.getQuestionContent())
                                .member(member)
                                .club(club)
                                .build();
        qnaQuestionRepository.save(qnaQuestion);
        return mappingResponse(club);
    }

    public List<QnaResponse> deleteQuestion(DeleteQnaQuestionRequest request, Long clubId) {
        QnaQuestion qnaQuestion = qnaQuestionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.QnA_QUESTION_NOT_FOUND));
        List<QnaAnswer> qnaAnswers = qnaAnswerRepository.findByQnaQuestion(qnaQuestion);
        for (QnaAnswer qnaAnswer : qnaAnswers)
            qnaAnswerRepository.delete(qnaAnswer);
        qnaQuestionRepository.delete(qnaQuestion);
        return mappingResponse(qnaQuestion.getClub());
    }

    public List<QnaResponse> registerAnswer(CreateQnaAnswerRequest request, Long clubId) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
        QnaQuestion qnaQuestion = qnaQuestionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.QnA_QUESTION_NOT_FOUND));
        Optional<Belong> belong = belongRepository.findByMemberAndClub(member, qnaQuestion.getClub());
        Boolean isManager;
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        if (belong.isEmpty()) {
            isManager = false;
        } else {
            if (belong.get().getPosition() == Position.NORMAL) {
                isManager = false;
            } else {
                isManager = true;
            }
        }
        QnaAnswer qnaAnswer = QnaAnswer.builder()
                .content(request.getAnswerContent())
                .isManager(isManager)
                .member(member)
                .qnaQuestion(qnaQuestion)
                .build();

        qnaAnswerRepository.save(qnaAnswer);
        return mappingResponse(qnaQuestion.getClub());
    }

    public List<QnaResponse> deleteAnswer(DeleteQnaAnswerRequest request, Long clubId) {
        QnaAnswer qnaAnswer = qnaAnswerRepository.findById(request.getAnswerId()).
                orElseThrow(() -> new BadRequestException(ErrorCode.QnA_ANSWER_NOT_FOUND));
        qnaAnswerRepository.delete(qnaAnswer);
        return mappingResponse(qnaAnswer.getQnaQuestion().getClub());
    }

    private List<QnaResponse> mappingResponse(Club club) {
        List<QnaQuestion> qnaQuestions = qnaQuestionRepository.findByClub(club);
        List<QnaResponse> responses = new ArrayList<>();

        for (QnaQuestion qnaQuestion : qnaQuestions) {
            List<QnaAnswer> qnaAnswers = qnaAnswerRepository.findByQnaQuestion(qnaQuestion);
            QnaResponse response = new QnaResponse();
            response.from(qnaQuestion, qnaAnswers);
            responses.add(response);
        }
        return responses;
    }
}