package com.hongik.joinhere.domain.qna.qna;

import com.hongik.joinhere.domain.qna.answer.dto.CreateQnaAnswerRequest;
import com.hongik.joinhere.domain.qna.answer.dto.DeleteQnaAnswerRequest;
import com.hongik.joinhere.domain.qna.qna.dto.QnaResponse;
import com.hongik.joinhere.domain.qna.question.dto.CreateQnaQuestionRequest;
import com.hongik.joinhere.domain.qna.question.dto.DeleteQnaQuestionRequest;
import com.hongik.joinhere.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QnaController {

    private final QnaService qnaService;

    @PostMapping("/clubs/{club-id}/qnas/questions")
    CommonResponse<List<QnaResponse>> registerQuestion(@RequestBody CreateQnaQuestionRequest request, @PathVariable(name = "club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.CREATED.value(), qnaService.registerQuestion(request, clubId));
    }

    @DeleteMapping("/clubs/{club-id}/qnas/questions")
    CommonResponse<List<QnaResponse>> deleteQuestion(@RequestBody DeleteQnaQuestionRequest request, @PathVariable(name = "club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), qnaService.deleteQuestion(request, clubId));
    }

    @PostMapping("/clubs/{club-id}/qnas/answers")
    CommonResponse<List<QnaResponse>> registerAnswer(@RequestBody CreateQnaAnswerRequest request, @PathVariable(name = "club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.CREATED.value(), qnaService.registerAnswer(request, clubId));
    }

    @DeleteMapping("/clubs/{club-id}/qnas/answers")
    CommonResponse<List<QnaResponse>> deleteAnswer(@RequestBody DeleteQnaAnswerRequest request, @PathVariable(name = "club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(),qnaService.deleteAnswer(request, clubId));
    }
}
