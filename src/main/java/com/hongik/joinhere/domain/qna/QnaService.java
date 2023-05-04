package com.hongik.joinhere.domain.qna;

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
import com.hongik.joinhere.domain.qna.dto.QnaResponse;
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

    public List<QnaResponse> registerQnaQuestion(CreateQnaQuestionRequest request, Long clubId) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.CLUB_NOT_FOUND));
        QnaQuestion qnaQuestion = QnaQuestion.builder()
                                .content(request.getQnaQuestionContent())
                                .member(member)
                                .club(club)
                                .build();
        qnaQuestionRepository.save(qnaQuestion);
        return mappingQnaResponse(club);
    }

    public List<QnaResponse> deleteQnaQuestion(DeleteQnaQuestionRequest request) {
        QnaQuestion qnaQuestion = qnaQuestionRepository.findById(request.getQnaQuestionId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.QnA_QUESTION_NOT_FOUND));
        List<QnaAnswer> qnaAnswers = qnaAnswerRepository.findByQnaQuestion(qnaQuestion);
        for (QnaAnswer qnaAnswer : qnaAnswers) {
            qnaAnswerRepository.delete(qnaAnswer);
        }
        qnaQuestionRepository.delete(qnaQuestion);
        return mappingQnaResponse(qnaQuestion.getClub());
    }

    public List<QnaResponse> registerQnaAnswer(CreateQnaAnswerRequest request) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
        QnaQuestion qnaQuestion = qnaQuestionRepository.findById(request.getQnaQuestionId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.QnA_QUESTION_NOT_FOUND));

        QnaAnswer qnaAnswer = QnaAnswer.builder()
                .content(request.getQnaAnswerContent())
                .isManager(isManager(belongRepository.findByMemberAndClub(member, qnaQuestion.getClub())))
                .member(member)
                .qnaQuestion(qnaQuestion)
                .build();
        qnaAnswerRepository.save(qnaAnswer);
        return mappingQnaResponse(qnaQuestion.getClub());
    }

    public List<QnaResponse> deleteQnaAnswer(DeleteQnaAnswerRequest request) {
        QnaAnswer qnaAnswer = qnaAnswerRepository.findById(request.getQnaAnswerId()).
                orElseThrow(() -> new BadRequestException(ErrorCode.QnA_ANSWER_NOT_FOUND));
        qnaAnswerRepository.delete(qnaAnswer);
        return mappingQnaResponse(qnaAnswer.getQnaQuestion().getClub());
    }

    private List<QnaResponse> mappingQnaResponse(Club club) {
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

    private Boolean isManager(Optional<Belong> belong) {
        if (belong.isEmpty()) {
            return false;
        }
        if (belong.get().getPosition() == Position.NORMAL) {
            return false;
        }
        return true;
    }
}