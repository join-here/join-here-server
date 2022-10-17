package com.hongik.joinhere.service;

import com.hongik.joinhere.dto.question.ShowQuestionResponse;
import com.hongik.joinhere.entity.Question;
import com.hongik.joinhere.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<ShowQuestionResponse> findQuestionsByAnnouncement(Long announcementId) {
        List<Question> questions = questionRepository.findByAnnouncementId(announcementId);
        List<ShowQuestionResponse> responses = new ArrayList<>();
        for (Question question : questions)
            responses.add(ShowQuestionResponse.from(question));
        return responses;
    }
}
