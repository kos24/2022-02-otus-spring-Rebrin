package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;

    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public void printQuestions() {
        questionDao.readQuestions().forEach(question -> System.out.printf("%nQuestion: %s%nPossible answers: %s%n",
                question.getQuestionAsked(), question.getPossibleAnswers()));
    }

}