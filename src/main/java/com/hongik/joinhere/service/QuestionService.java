package com.hongik.joinhere.service;

import com.hongik.joinhere.dto.question.ShowQuestionResponse;
import com.hongik.joinhere.entity.Question;
import com.hongik.joinhere.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<ShowQuestionResponse> findQuestionsByAnnouncement(Long announcementId) {
        List<Question> questions = questionRepository.findByAnnouncementId(announcementId);
        List<ShowQuestionResponse> responses = new ArrayList<>();
        for (Question question : questions)
            responses.add(ShowQuestionResponse.from(question));
        return responses;
    }
}
