package ru.otus.spring.service;

import ru.otus.spring.dao.QuestionDao;

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