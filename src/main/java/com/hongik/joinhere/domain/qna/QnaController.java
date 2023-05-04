package com.hongik.joinhere.domain.qna;

import com.hongik.joinhere.domain.qna.answer.dto.CreateQnaAnswerRequest;
import com.hongik.joinhere.domain.qna.answer.dto.DeleteQnaAnswerRequest;
import com.hongik.joinhere.domain.qna.dto.QnaResponse;
import com.hongik.joinhere.domain.qna.question.dto.CreateQnaQuestionRequest;
import com.hongik.joinhere.domain.qna.question.dto.DeleteQnaQuestionRequest;
import com.hongik.joinhere.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QnaController {

    private final QnaService qnaService;

    @PostMapping("/clubs/{club-id}/qnas/questions")
    @ResponseStatus(HttpStatus.CREATED)
    CommonResponse<List<QnaResponse>> createQnaQuestion(@RequestBody CreateQnaQuestionRequest request,
                                                        @PathVariable("club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.CREATED.value(), qnaService.registerQnaQuestion(request, clubId));
    }

    @DeleteMapping("/clubs/{club-id}/qnas/questions")
    CommonResponse<List<QnaResponse>> deleteQnaQuestion(@RequestBody DeleteQnaQuestionRequest request,
                                                        @PathVariable("club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), qnaService.deleteQnaQuestion(request));
    }

    @PostMapping("/clubs/{club-id}/qnas/answers")
    @ResponseStatus(HttpStatus.CREATED)
    CommonResponse<List<QnaResponse>> createQnaAnswer(@RequestBody CreateQnaAnswerRequest request,
                                                      @PathVariable("club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.CREATED.value(), qnaService.registerQnaAnswer(request));
    }

    @DeleteMapping("/clubs/{club-id}/qnas/answers")
    CommonResponse<List<QnaResponse>> deleteQnaAnswer(@RequestBody DeleteQnaAnswerRequest request,
                                                      @PathVariable("club-id") Long clubId) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(),qnaService.deleteQnaAnswer(request));
    }
}
