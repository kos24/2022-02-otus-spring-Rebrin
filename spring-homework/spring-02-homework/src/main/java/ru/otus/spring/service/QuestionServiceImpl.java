package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;
    private final IOService ioService;

    public QuestionServiceImpl(QuestionDao questionDao, IOService ioService) {
        this.questionDao = questionDao;
        this.ioService = ioService;
    }

    public void printQuestions() {
        questionDao.readQuestions().forEach(question ->
                ioService.print(String.format("Question: %s%nPossible answers: %s",
                        question.getQuestionText(), question.getPossibleAnswers())));
    }

}